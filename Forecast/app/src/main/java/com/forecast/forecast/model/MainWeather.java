package com.forecast.forecast.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeffkungu on 15/04/2019.
 */

class MainWeather {
    @SerializedName("temp")
    public Float averageTemperature;
    @SerializedName("pressure")
    public Float pressure;
    @SerializedName("humidity")
    public Integer humidity;
    @SerializedName("temp_min")
    public Float tempMin;
    @SerializedName("temp_max")
    public Float tempMax;
    @SerializedName("sea_level")
    public Float seaLevel;
    @SerializedName("grnd_level")
    public Float grndLevel;

    public Float getAverageTemperature() {
        return this.averageTemperature;
    }

    public Float getPressure() {
        return this.pressure;
    }

    public Integer getHumidity() {
        return this.humidity;
    }
}
