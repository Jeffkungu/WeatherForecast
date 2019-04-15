package com.forecast.forecast.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class Sun {
    @SerializedName("message")
    public Float message;
    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public Integer sunrise;
    @SerializedName("sunset")
    public Integer sunset;

}
