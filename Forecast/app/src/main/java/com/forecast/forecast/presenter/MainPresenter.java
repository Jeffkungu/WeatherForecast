package com.forecast.forecast.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;

import com.forecast.forecast.Contract;
import com.forecast.forecast.Contract.MainView;
import com.forecast.forecast.R;
import com.forecast.forecast.R.string;
import com.forecast.forecast.api.OpenWeatherApi;
import com.forecast.forecast.api.Service;
import com.forecast.forecast.model.OpenWeatherResponse;
import com.forecast.forecast.model.WeatherDetails;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class MainPresenter implements Contract.MainViewEventlistener {
    private static final String USER_INFORMATION = "User Info";
    private static final String OPEN_WEATHER_KEY = "Google key";
    private Context context;
    private Contract.MainView mainView;
    private SharedPreferences sharedPreferences;

    public MainPresenter(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;

        sharedPreferences = context.getSharedPreferences(USER_INFORMATION,
                Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(OPEN_WEATHER_KEY, "4f1cde5084a931c9e34e2fa4153e1e52");
        editor.apply();
    }

    public boolean isGoogleServiceAvailable() {
        int serviceAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (serviceAvailable == ConnectionResult.SUCCESS){
            return true;
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(serviceAvailable)){
            mainView.displayGoogleServiceError(serviceAvailable);
        } else {
            mainView.displayMessage(context.getString(R.string.maps_error));
        }
        return false;
    }

    @Override
    public void checkGoogleServiceAvailability() {
        if (isGoogleServiceAvailable()){
            mainView.initialiseViews();
        } else {
            mainView.displayMessage(context.getString(R.string.maps_error));
        }
    }

    @Override
    public void findLocation(String locationName) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(locationName, 1);
        } catch (IOException e) {
            mainView.displayMessage(context.getString(R.string.error_fetching_locations));
        }
        if (!addressList.isEmpty()){
            Address address = addressList.get(0);
            mainView.moveCameraView(new LatLng(address.getLatitude(), address.getLongitude()), 5f,
                    address.getAddressLine(0));
        } else {
            mainView.displayMessage(context.getString(R.string.enter_valid_location_error));
        }
    }

    @Override
    public void getWetherDetails(String locationName){
        if (!locationName.isEmpty()){
            Geocoder geocoder = new Geocoder(context);
            List<Address> addressList = new ArrayList<>();
            try {
                addressList = geocoder.getFromLocationName(locationName, 1);
            } catch (IOException e) {
                mainView.displayMessage(context.getString(R.string.error_fetching_locations));
            }

            if (!addressList.isEmpty()){
                Address address = addressList.get(0);
                String latitude = String.valueOf(address.getLatitude());
                String longitude = String.valueOf(address.getLongitude());
                getWeatherDetails(locationName, latitude, longitude);
            } else {
                mainView.displayMessage(context.getString(R.string.enter_valid_location_error));
            }
        } else {
            mainView.displayMessage(context.getString(R.string.enter_valid_location_error));
        }
    }

    private void getWeatherDetails(final String locationName, String latitude, String longitude) {
        String apiKey = sharedPreferences.getString(OPEN_WEATHER_KEY, null);
        Service openWeatherService = OpenWeatherApi.getClient().create(Service.class);
        Call<OpenWeatherResponse> call = openWeatherService.getWeatherDetails(latitude,
                longitude, apiKey);
        call.enqueue(new Callback<OpenWeatherResponse>() {
            @Override
            public void onResponse(Call<OpenWeatherResponse> call, Response<OpenWeatherResponse> response) {
                OpenWeatherResponse openWeatherResponse = response.body();
                if(openWeatherResponse != null) {
                    displayWeatherDetails(locationName, openWeatherResponse);
                } else {
                    mainView.displayMessage(context.getString(string.response_body_null));
                }
            }

            @Override
            public void onFailure(Call<OpenWeatherResponse> call, Throwable t) {
                mainView.displayMessage(context.getString(string.internet_connectivity_error));
            }
        });
    }

    private void displayWeatherDetails(String locationName, OpenWeatherResponse openWeatherResponse) {
        String skyConditions = "";
        if(!openWeatherResponse.getWeatherDetails().isEmpty()){
            WeatherDetails weatherDetails = openWeatherResponse.getWeatherDetails().get(0);
            skyConditions = weatherDetails.getDescription();
        }
        String averaTemp = String.valueOf(openWeatherResponse.getMain().getAverageTemperature()) + " f";
        String pressure = String.valueOf(openWeatherResponse.getMain().getPressure()) + " p";
        String humidity = String.valueOf(openWeatherResponse.getMain().getHumidity()) + " gm/ml";
        String windSpeed = String.valueOf(openWeatherResponse.getWind().getSpeed()) + " km/hr";

        mainView.displayWetherDetails(locationName, skyConditions, averaTemp, pressure, humidity, windSpeed);
    }
}
