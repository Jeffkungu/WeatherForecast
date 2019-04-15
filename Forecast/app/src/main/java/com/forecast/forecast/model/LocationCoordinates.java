package com.forecast.forecast.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class LocationCoordinates {
    @SerializedName("lon")
    public Integer longitude;
    @SerializedName("lat")
    public Integer latitude;

    public Integer getLongitude() {
        return this.longitude;
    }

    public Integer getLatitude() {
        return this.latitude;
    }
}
