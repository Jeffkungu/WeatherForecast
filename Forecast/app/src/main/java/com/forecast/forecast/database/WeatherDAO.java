package com.forecast.forecast.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Created by jeffkungu on 15/04/2019.
 */
@Dao
public interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(WeatherTable weatherTable);

    @Query("SELECT * from weather_table")
    List<WeatherTable> getAllBookmarkedLocations();

    @Query("DELETE FROM weather_table")
    void deleteAllBookmarkedLocations();
}
