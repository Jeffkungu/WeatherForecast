package com.forecast.forecast.api;

import com.forecast.forecast.model.OpenWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public interface Service {
    @GET("weather")
    Call<OpenWeatherResponse> getWeatherDetails(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("appid") String appId);
}
