package com.forecast.forecast.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class LocationCoordinates {
    @SerializedName("lon")
    public Float longitude;
    @SerializedName("lat")
    public Float latitude;

    public Float getLongitude() {
        return this.longitude;
    }

    public Float getLatitude() {
        return this.latitude;
    }
}
