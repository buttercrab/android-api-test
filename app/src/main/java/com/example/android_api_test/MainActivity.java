package com.example.android_api_test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class MainActivity extends Activity {

    TextView time, date, temp, minmaxtemp;
    ImageView weather_icon;
    RecyclerView forecast;

    GPSTracker gpsTracker;

    Context main;

    Handler updateTime = new Handler(Looper.getMainLooper()) {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void handleMessage(Message msg) {
            Calendar calendar = Calendar.getInstance();
            time.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
            date.setText(String.format("%d/%d/%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)));
            sendEmptyMessageDelayed(1, 100);
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main = this;
        gpsTracker = new GPSTracker(main);

        time = findViewById(R.id.current_time);
        date = findViewById(R.id.current_date);
        temp = findViewById(R.id.temp);
        minmaxtemp = findViewById(R.id.minmaxtemp);
        forecast = findViewById(R.id.weather_forecast);
        weather_icon = findViewById(R.id.weather_icon);

        updateTime.sendEmptyMessage(1);
        loadWeatherData();
    }

    protected void loadWeatherData() {
        gpsTracker.loadLocation();
        double lat = gpsTracker.getLatitude();
        double lon = gpsTracker.getLongitude();

        CurrentWeatherData currentWeather = OpenWeatherAPI.getCurrentWeatherData((float)lat, (float)lon);

    }
}
