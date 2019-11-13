package com.example.android_api_test;

import android.os.Handler;

import java.text.ParseException;
import java.util.Date;

class CurrentWeatherData {
    Coord coord;
    Weather[] weather;
    String base;
    Main main;
    int visibility;
    Wind wind;
    Cloud clouds;
    float dt;
    Sys sys;
    int timezone;
    int id;
    String name;
    int cod;

    static class Coord {
        float lat;
        float lon;
    }

    static class Weather {
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

    static class Sys {
        int type;
        int id;
        String country;
        int sunrise;
        int sunset;
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
        Clouds clouds;
        Wind wind;
        Sys sys;
        String dt_txt;

        public Date getDate() throws ParseException {
            // using simple date format
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

    private static String api_key = ""; // put api key

    public static String getIconURL(String icon) {
        return "https://openweathermap.org/img/wn/" + icon + "@2x.png";
    }

    public static void getCurrentWeather(final double lat, final double lon, final Handler handler) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        // get data and parse json
                    }
                }
        ).start();
    }

    public static void getForecast(final double lat, final double lon, final Handler handler) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        // get data and parse json
                    }
                }
        ).start();
    }
}
