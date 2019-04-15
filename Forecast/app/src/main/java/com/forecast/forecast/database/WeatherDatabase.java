package com.forecast.forecast.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by jeffkungu on 15/04/2019.
 */
@Database(entities = {WeatherTable.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase{
    private static WeatherDatabase instance;
    public abstract WeatherDAO weatherDAO();

    public static synchronized WeatherDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherDatabase.class, "weather_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
