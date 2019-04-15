package com.forecast.forecast;

import com.forecast.forecast.database.WeatherTable;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

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
        void closeWindow();
    }
    interface MainViewEventlistener {
        void checkGoogleServiceAvailability();
        void findLocation(String locationName);
        void getWetherDetails(String locationName);
        void saveWeatherDetails(String locationName);
    }

    interface BookmarkedLocationsView{
        void displayMessage(String message);
        void populateRecyclerView(List<WeatherTable> weatherTables);
        void openMapPage();
    }

    interface BookmarkedLocationsEventListener{
        void displayLocations();
        void deleteLocations();
    }
}
