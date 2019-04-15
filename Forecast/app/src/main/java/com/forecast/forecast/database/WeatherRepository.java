package com.forecast.forecast.database;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class WeatherRepository {
    private WeatherDAO weatherDAO;
    private MutableLiveData<List<WeatherTable>> allWeather;

    public WeatherRepository(Context constext) {
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(constext);
        weatherDAO = weatherDatabase.weatherDAO();
        allWeather = new MutableLiveData<>();
        allWeather.postValue(weatherDAO.getAllBookmarkedLocations());
    }

    public void insertWeather(WeatherTable weatherTable) {
        new InsertWeatherAsyncTask(weatherDAO).execute(weatherTable);
    }

    public MutableLiveData<List<WeatherTable>> getAllBookmarkedWeather() {
        return allWeather;
    }

    public void deleteAllLocations() {
        new DeleteAllWeatherAsyncTask(weatherDAO).execute();
    }

    private static class DeleteAllWeatherAsyncTask extends AsyncTask<Void, Void, Void> {
        private WeatherDAO weatherDAO;

        public DeleteAllWeatherAsyncTask(WeatherDAO weatherDAO) {
            this.weatherDAO = weatherDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            weatherDAO.deleteAllBookmarkedLocations();
            return null;
        }
    }

    private static class InsertWeatherAsyncTask extends AsyncTask<WeatherTable, Void, Void> {
        private WeatherDAO weatherDAO;

        public InsertWeatherAsyncTask(WeatherDAO weatherDAO) {
            this.weatherDAO = weatherDAO;
        }

        @Override
        protected Void doInBackground(WeatherTable... weatherTables) {
            weatherDAO.insertWeather(weatherTables[0]);
            return null;
        }
    }
}
