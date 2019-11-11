package com.example.android_api_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private ArrayList<ForecastData.List> dataset;

    public ForecastAdapter(ArrayList<ForecastData.List> list) {
        dataset = list;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        public TextView time, temp;
        public ImageView icon;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            this.time = itemView.findViewById(R.id.forecast_time);
            this.temp = itemView.findViewById(R.id.forecast_temp);
            this.icon = itemView.findViewById(R.id.forecast_weather_icon);
        }
    }

    @NonNull
    @Override
    public ForecastAdapter.ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent, false);

        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
            holder.time.setText(dateFormat.format(dataset.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.temp.setText(String.format("%s°", (int) dataset.get(position).main.temp - 273));
        new DownloadImageTask(holder.icon).execute(OpenWeatherAPI.getIconURL(dataset.get(position).weather[0].icon));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
