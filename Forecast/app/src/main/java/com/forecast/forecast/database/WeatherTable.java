package com.forecast.forecast.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by jeffkungu on 15/04/2019.
 */
@Entity(tableName ="weather_table")
public class WeatherTable {
    @PrimaryKey
    @NonNull
    private String locationName;
    private String skyCondition;
    private String averageTemp;
    private String pressure;
    private String humidity;
    private String windSpeed;

    public WeatherTable(@NonNull String locationName, String skyCondition, String averageTemp,
                        String pressure, String humidity, String windSpeed) {
        this.locationName = locationName;
        this.skyCondition = skyCondition;
        this.averageTemp = averageTemp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    @NonNull
    public String getLocationName() {
        return this.locationName;
    }

    public String getSkyCondition() {
        return this.skyCondition;
    }

    public String getAverageTemp() {
        return this.averageTemp;
    }

    public String getPressure() {
        return this.pressure;
    }

    public String getHumidity() {
        return this.humidity;
    }

    public String getWindSpeed() {
        return this.windSpeed;
    }
}
