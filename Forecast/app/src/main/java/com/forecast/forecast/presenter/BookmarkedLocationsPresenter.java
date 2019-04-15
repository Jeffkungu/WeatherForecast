package com.forecast.forecast.presenter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.forecast.forecast.Contract;
import com.forecast.forecast.Contract.BookmarkedLocationsView;
import com.forecast.forecast.R;
import com.forecast.forecast.database.WeatherRepository;
import com.forecast.forecast.database.WeatherTable;

import java.util.List;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class BookmarkedLocationsPresenter implements Contract.BookmarkedLocationsEventListener{
    private Context context;
    private Contract.BookmarkedLocationsView bookmarkedLocationsView;
    private WeatherRepository weatherRepository;

    public BookmarkedLocationsPresenter(Context context, BookmarkedLocationsView bookmarkedLocationsView) {
        this.context = context;
        this.bookmarkedLocationsView = bookmarkedLocationsView;
        weatherRepository = new WeatherRepository(context);
    }

    @Override
    public void displayLocations() {
        weatherRepository.getAllBookmarkedWeather().observe((LifecycleOwner) context, new Observer<List<WeatherTable>>() {
            @Override
            public void onChanged(@Nullable List<WeatherTable> weatherTables) {
                bookmarkedLocationsView.populateRecyclerView(weatherTables);
            }
        });
    }

    @Override
    public void deleteLocations() {
        weatherRepository.deleteAllLocations();
        bookmarkedLocationsView.displayMessage(context.getString(R.string.locations_deleted));
        bookmarkedLocationsView.openMapPage();
    }
}
