package com.example.android_api_test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends Activity {

    TextView time, date, temp, minmaxtemp, location;
    ImageView weather_icon;
    RecyclerView forecast;
    Button refresh;

    ArrayList<ForecastData.List> data;
    ForecastAdapter forecastAdapter;

    GPSTracker gpsTracker;

    Handler updateTime = new Handler(Looper.getMainLooper()) {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void handleMessage(Message msg) {
            Calendar calendar = Calendar.getInstance();
            time.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
            date.setText(String.format("%d/%d/%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)));
            sendEmptyMessageDelayed(1, 100);
        }
    };

    Handler updateWeather = new Handler(Looper.getMainLooper()) {
        @SuppressLint("DefaultLocale")
        @Override
        public void handleMessage(Message msg) {
            CurrentWeatherData currentWeather = (CurrentWeatherData) msg.obj;
            temp.setText(String.format("%.1f°C", currentWeather.main.temp - 273.15));
            minmaxtemp.setText(String.format("%.1f°C/%.1f°C", currentWeather.main.temp_min - 273.15,
                    currentWeather.main.temp_max - 273.15));
            new DownloadImageTask((ImageView) findViewById(R.id.weather_icon)).execute(OpenWeatherAPI.getIconURL(currentWeather.weather[0].icon));
            location.setText(currentWeather.name + ", " + currentWeather.sys.country);
        }
    };

    Handler updateForecast = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            ForecastData forecastData = (ForecastData) msg.obj;
            data.clear();
            data.addAll(Arrays.asList(forecastData.list));
            forecastAdapter.notifyDataSetChanged();
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gpsTracker = new GPSTracker(this);
        data = new ArrayList<>();

        LinearLayoutManager forecastManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        forecastAdapter = new ForecastAdapter(data);

        time = findViewById(R.id.current_time);
        date = findViewById(R.id.current_date);
        temp = findViewById(R.id.temp);
        minmaxtemp = findViewById(R.id.minmaxtemp);
        refresh = findViewById(R.id.refresh);
        forecast = findViewById(R.id.weather_forecast);
        weather_icon = findViewById(R.id.weather_icon);
        location = findViewById(R.id.location);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeatherData();
            }
        });
        forecast.setLayoutManager(forecastManager);
        forecast.setAdapter(forecastAdapter);

        updateTime.sendEmptyMessage(1);
        loadWeatherData();
    }

    protected void loadWeatherData() {
        gpsTracker.loadLocation();
        double lat = gpsTracker.getLatitude();
        double lon = gpsTracker.getLongitude();

        OpenWeatherAPI.getCurrentWeather((float) lat, (float) lon, updateWeather);
        OpenWeatherAPI.getForecast(lat, lon, updateForecast);
    }
}
