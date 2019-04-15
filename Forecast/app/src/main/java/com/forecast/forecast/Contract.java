package com.forecast.forecast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public interface Contract {
    interface MainView{
        void displayGoogleServiceError(int serviceAvailable);
        void displayMessage(String message);
        void initialiseViews();
        void moveCameraView(LatLng latLng, float zoom, String locationName);

        void displayWetherDetails(String locationName, String skyConditions, String averageTemp,
                                  String pressure, String humidity, String windSpeed);
    }
    interface MainViewEventlistener {
        void checkGoogleServiceAvailability();
        void findLocation(String locationName);
        void getWetherDetails(String locationName);
    }
}
