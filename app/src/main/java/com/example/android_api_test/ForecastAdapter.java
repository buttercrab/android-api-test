package com.example.android_api_test;

import android.graphics.Bitmap;
import android.util.Pair;
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
    private ArrayList<Pair<ForecastData.List, Bitmap>> dataset;

    public ForecastAdapter(ArrayList<Pair<ForecastData.List, Bitmap>> list) {
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
            holder.time.setText(dateFormat.format(dataset.get(position).first.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.temp.setText(String.format("%s°", (int) dataset.get(position).first.main.temp - 273));

        if (dataset.get(position).second == null) {
            new DownloadImageTask(holder.icon, dataset.get(position).second)
                    .execute(OpenWeatherAPI.getIconURL(dataset.get(position).first.weather[0].icon));
        } else {
            System.out.println("bitmap updated in position " + position);
            holder.icon.setImageBitmap(dataset.get(position).second);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
