package com.forecast.forecast.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forecast.forecast.R;
import com.forecast.forecast.database.WeatherTable;

import java.util.List;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class BookmarkedLocationsAdapter extends RecyclerView.Adapter<BookmarkedLocationsAdapter.MyViewHolder> {
    private List<WeatherTable> weatherTables;
    private Context context;

    public BookmarkedLocationsAdapter(List<WeatherTable> weatherTables, Context context) {
        this.weatherTables = weatherTables;
        this.context = context;
    }

    @Override
    public BookmarkedLocationsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_locations, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(BookmarkedLocationsAdapter.MyViewHolder holder, int position) {
        holder.locationName.setText(weatherTables.get(position).getLocationName());
        holder.skyCondition.setText(weatherTables.get(position).getSkyCondition());
        holder.temerature.setText(weatherTables.get(position).getAverageTemp());
    }

    @Override
    public int getItemCount() {
        return weatherTables.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView locationName;
        TextView temerature;
        TextView skyCondition;


        public MyViewHolder(View itemView) {
            super(itemView);

            locationName = itemView.findViewById(R.id.locationName);
            temerature = itemView.findViewById(R.id.temerature);
            skyCondition = itemView.findViewById(R.id.skyCondition);
        }
    }
}
