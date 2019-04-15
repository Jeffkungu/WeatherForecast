package com.forecast.forecast;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.forecast.forecast.presenter.MainPresenter;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Contract.MainView, View.OnClickListener, OnMapReadyCallback, TextView.OnEditorActionListener {
    private ConstraintLayout constraintWeatherInfo;
    private FloatingActionButton fab1, fab2;
    private ImageView getWeatherImage;
    private Contract.MainViewEventlistener eventlistener;
    private boolean permissionGranted = false;
    private GoogleMap map;
    private FusedLocationProviderClient locationProviderClient;
    private AutoCompleteTextView autoCompleteSearch;
    private MarkerOptions markerOptions;
    private TextView txtWeatherTitle, txtSkyConditionValue, txtPressureValue, txtHumidityValue, txtWindSpeedValue,
            txtSave, txtCancel, txtTemperatureValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventlistener = new MainPresenter(this, this);
        eventlistener.checkGoogleServiceAvailability();

        checkPermissions();
    }

    @Override
    public void initialiseViews() {
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        getWeatherImage = findViewById(R.id.getWeatherImage);
        autoCompleteSearch = findViewById(R.id.autoCompleteSearch);
        constraintWeatherInfo = findViewById(R.id.constraintWeatherInfo);
        txtSkyConditionValue = findViewById(R.id.txtSkyConditionValue);
        txtPressureValue = findViewById(R.id.txtPressureValue);
        txtHumidityValue = findViewById(R.id.txtHumidityValue);
        txtWindSpeedValue = findViewById(R.id.txtWindSpeedValue);
        txtTemperatureValue = findViewById(R.id.txtTemperatureValue);
        txtWeatherTitle = findViewById(R.id.txtWeatherTitle);
        txtSave = findViewById(R.id.txtSave);
        txtCancel = findViewById(R.id.txtCancel);

        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        getWeatherImage.setOnClickListener(this);
        txtSave.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        autoCompleteSearch.setOnEditorActionListener(this);
    }

    private void getUserLocation() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (permissionGranted) {
                Task task = locationProviderClient.getLastLocation();
                task.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Location userLocation = (Location) task.getResult();
                            if (userLocation != null){
                                LatLng latLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
                                moveCameraView(latLng, 20f, "Current Location");
                            }
                        } else {
                            displayMessage(getString(R.string.find_location_error));
                        }
                    }
                });
            }
        } catch (SecurityException e) {

        }
    }

    private void checkPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
                initialiseMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    private void initialiseMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            permissionGranted = false;
                            return;
                        }
                    }
                    permissionGranted = true;
                    initialiseMap();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void displayGoogleServiceError(int serviceAvailable) {
        Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, serviceAvailable, 1000);
        dialog.show();
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab1:
                displayMessage("Floating button clicked");
                break;

            case R.id.fab2:
                displayMessage("Floating button clicked");
                break;

            case R.id.getWeatherImage:
                eventlistener.getWetherDetails(autoCompleteSearch.getText().toString());
                break;

            case R.id.txtCancel:
                constraintWeatherInfo.setVisibility(View.INVISIBLE);
                break;

            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (permissionGranted) {
            getUserLocation();
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    @Override
    public void moveCameraView(LatLng latLng, float zoom, String locationName) {
        map.clear();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        markerOptions = new MarkerOptions()
                .position(latLng)
                .title(locationName);
        map.addMarker(markerOptions);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE){
            String locationName = autoCompleteSearch.getText().toString();
            eventlistener.findLocation(locationName);
        }
        return false;
    }

    @Override
    public void displayWetherDetails(String locationName, String skyConditions, String averageTemp,
                                     String pressure, String humidity, String windSpeed){
        constraintWeatherInfo.setVisibility(View.VISIBLE);
        txtWeatherTitle.setText(locationName +  " " +getResources().getString(R.string.weather_condition));
        txtSkyConditionValue.setText(skyConditions);
        txtTemperatureValue.setText(averageTemp);
        txtPressureValue.setText(pressure);
        txtHumidityValue.setText(humidity);
        txtWindSpeedValue.setText(windSpeed);
    }
}
