package com.forecast.forecast.presenter;

import android.content.Context;

import com.forecast.forecast.Contract;
import com.forecast.forecast.Contract.MainView;
import com.forecast.forecast.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by jeffkungu on 15/04/2019.
 */

public class MainPresenter implements Contract.MainViewEventlistener {
    private Context context;
    private Contract.MainView mainView;

    public MainPresenter(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
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
}
