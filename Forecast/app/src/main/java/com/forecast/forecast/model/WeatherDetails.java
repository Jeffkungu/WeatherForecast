package com.forecast.forecast.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class WeatherDetails {
    @SerializedName("id")
    public Integer id;
    @SerializedName("main")
    public String mainWeather;
    @SerializedName("description")
    public String description;
    @SerializedName("icon")
    public String weatherIcon;

    public String getMainWeather() {
        return this.mainWeather;
    }

    public String getDescription() {
        return this.description;
    }
}
