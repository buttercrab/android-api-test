package com.example.android_api_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private ArrayList<ForecastData.List> dataset;
    Context context;

    public ForecastAdapter(ArrayList<ForecastData.List> list, Context context) {
        dataset = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        System.out.println(position + " change");
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm ");
            holder.time.setText(dateFormat.format(dataset.get(position).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(" MM/dd ");
            String today = dateFormat.format(dataset.get(position).getDate());
            String yesterday = "";
            if (position != 0)
                yesterday = dateFormat.format(dataset.get(position - 1).getDate());
            if (!today.equals(yesterday))
                holder.date.setText(today);
            else
                holder.date.setText("");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.temp.setText(String.format("%s°", (int) dataset.get(position).main.temp - 273));
        holder.temp.setText(dataset.get(position).weather[0].icon);
//        new DownloadImageTask(holder.icon).execute(OpenWeatherAPI.getIconURL(dataset.get(position).weather[0].icon), position + "");
        Glide.with(context)
                .load(OpenWeatherAPI.getIconURL(dataset.get(position).weather[0].icon))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(new RequestOptions().override(100, 100))
                .into(holder.icon);
    }

    @NonNull
    @Override
    public ForecastAdapter.ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent, false);
        return new ForecastViewHolder(itemView);
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        public TextView time, temp, date;
        public ImageView icon;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            this.time = itemView.findViewById(R.id.forecast_time);
            this.temp = itemView.findViewById(R.id.forecast_temp);
            this.date = itemView.findViewById(R.id.forecast_date);
            this.icon = itemView.findViewById(R.id.forecast_weather_icon);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
