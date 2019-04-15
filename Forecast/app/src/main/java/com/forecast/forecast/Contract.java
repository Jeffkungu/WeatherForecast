package com.forecast.forecast;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public interface Contract {
    interface MainView{
        void displayGoogleServiceError(int serviceAvailable);
        void displayMessage(String message);
        void initialiseViews();
    }
    interface MainViewEventlistener {
        void checkGoogleServiceAvailability();
    }
}
