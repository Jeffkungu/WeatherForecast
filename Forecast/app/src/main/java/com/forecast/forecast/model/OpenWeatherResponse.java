package com.forecast.forecast.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class OpenWeatherResponse {
    @SerializedName("coord")
    public LocationCoordinates locationCoordinates;
    @SerializedName("weather")
    public List<WeatherDetails> weatherDetails;
    @SerializedName("base")
    public String base;
    @SerializedName("main")
    public MainWeather main;
    @SerializedName("wind")
    public WindDetails wind;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("dt")
    public Integer dt;
    @SerializedName("sys")
    public Sun sys;
    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String locationName;
    @SerializedName("cod")
    public Integer locationCode;

    public List<WeatherDetails> getWeatherDetails() {
        return this.weatherDetails;
    }

    public MainWeather getMain() {
        return this.main;
    }

    public WindDetails getWind() {
        return this.wind;
    }
}
