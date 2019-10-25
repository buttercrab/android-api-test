package com.example.android_api_test;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    LocationManager locationManager;

    TextView time, date;

    Handler updateTime = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Calendar calendar = Calendar.getInstance();
            time.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
            date.setText(String.format("%d/%d/%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)));
            sendEmptyMessageDelayed(1, 100);
        }
    };

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
        time = findViewById(R.id.current_time);
        date = findViewById(R.id.current_date);
        updateTime.sendEmptyMessage(1);
        loadWeatherData();
    }

    protected void loadWeatherData() {

    }
}
