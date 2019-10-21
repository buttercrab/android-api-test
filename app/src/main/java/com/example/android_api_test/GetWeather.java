package com.example.android_api_test;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Weather {
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

public class GetWeather {

    public static Weather getWeather(float lat, float lon) throws IOException {
        String api_key = "b00bccf2cfe104ad03e1ce916fe12cab";
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + api_key);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String data = br.readLine();

        Gson gson = new Gson();
        return gson.fromJson(data, Weather.class);
    }
}
