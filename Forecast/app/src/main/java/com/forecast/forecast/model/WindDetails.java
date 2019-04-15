package com.forecast.forecast.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class WindDetails {
    @SerializedName("speed")
    public Float speed;
    @SerializedName("deg")
    public Float deg;

    public Float getSpeed() {
        return this.speed;
    }
}
