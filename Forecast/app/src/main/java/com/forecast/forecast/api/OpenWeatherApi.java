package com.forecast.forecast.api;

import com.forecast.forecast.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class OpenWeatherApi {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            OkHttpClient.Builder okhttpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            if (BuildConfig.DEBUG) {
                okhttpClient.addInterceptor(loggingInterceptor);
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpClient.build())
                    .build();
        }
        return retrofit;
    }
}
