package com.example.android_api_test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends Activity {

    LocationManager locationManager;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            try {
                CurrentWeatherData weather = OpenWeatherAPI.getCurrentWeatherData((float)lat, (float)lon);
                float t = weather.main.temp;
                float mint = weather.main.temp_min;
                float maxt = weather.main.temp_max;
                temp.setText(String.format("%.1f°C", t));
                minmaxtemp.setText(String.format("%.1f°C/%.1f°C", mint, maxt));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    TextView time, date, temp, minmaxtemp;
    ImageView weather_icon;
    RecyclerView forecast;

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

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new AlertDialog.Builder(this)
                    .setTitle("GPS Disabled")
                    .setMessage("Please Turn on your GPS to get weather information of your location.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);

        time = findViewById(R.id.current_time);
        date = findViewById(R.id.current_date);
        temp = findViewById(R.id.temp);
        minmaxtemp = findViewById(R.id.minmaxtemp);
        forecast = findViewById(R.id.weather_forecast);
        weather_icon = findViewById(R.id.weather_icon);

        updateTime.sendEmptyMessage(1);
    }
}
