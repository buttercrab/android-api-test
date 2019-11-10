package com.example.android_api_test;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class CurrentWeatherData {
    Detail[] weather;
    Main main;
    Wind wind;
    Cloud clouds;

    static class Detail {
        int id;
        String main;
        String description;
        String icon;
    }

    static class Main {
        float temp;
        int pressure;
        int humidity;
        float temp_min;
        float temp_max;
    }

    static class Wind {
        float speed;
        float deg;
    }

    static class Cloud {
        int all;
    }
}

class ForecastData {
    City city;
    Coord coord;
    String country;
    int timezone;
    String cod;
    int message;
    int cnt;
    List[] list;

    public static class City {
        int id;
        String name;
    }

    public static class Coord {
        float lon;
        float lat;
    }

    public static class List {
        String dt;
        Main main;
        Weather[] weather;
        Clouds cLouds;
        Wind wind;
        Sys sys;
        String dt_text;

        public Date getDate() throws ParseException {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(this.dt_text);
        }

        static class Main {
            float temp;
            float temp_min;
            float temp_max;
            float pressure;
            float sea_level;
            float grnd_level;
            float humidity;
            float temp_kf;
        }

        static class Weather {
            int id;
            String main;
            String description;
            String icon;
        }

        static class Clouds {
            int all;
        }

        static class Wind {
            float speed;
            float deg;
        }

        static class Sys {
            String pod;
        }
    }
}

public class OpenWeatherAPI {

    private static String api_key = "b00bccf2cfe104ad03e1ce916fe12cab";

    public static String getIconURL(String icon) {
        return "https://openweathermap.org/img/wn/" + icon + "@2x.png";
    }

    public static void getCurrentWeather(final double lat, final double lon, final Handler handler) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + api_key);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();

                            con.setRequestMethod("GET");
                            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String data = br.readLine();

                            Gson gson = new Gson();
                            final CurrentWeatherData weatherData = gson.fromJson(data, CurrentWeatherData.class);
                            Message message = new Message();
                            message.obj = weatherData;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    public static void getForecast(final double lat, final double lon, final Handler handler) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + api_key);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();

                            con.setRequestMethod("GET");
                            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String data = br.readLine();

                            Gson gson = new Gson();
                            final ForecastData forecastData = gson.fromJson(data, ForecastData.class);
                            Message message = new Message();
                            message.obj = forecastData;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}
