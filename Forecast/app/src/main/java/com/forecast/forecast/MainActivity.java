package com.forecast.forecast;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.forecast.forecast.presenter.MainPresenter;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements Contract.MainView, View.OnClickListener, OnMapReadyCallback {
    private ConstraintLayout mainConstraint;
    private FloatingActionButton fab1;
    private Contract.MainViewEventlistener eventlistener;
    private boolean permissionGranted = false;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        mainConstraint = findViewById(R.id.mainConstraint);
        eventlistener = new MainPresenter(this, this);
        eventlistener.checkGoogleServiceAvailability();

        checkPermissions();
    }

    @Override
    public void initialiseViews() {
        fab1 = findViewById(R.id.fab1);

        fab1.setOnClickListener(this);
    }

    private void checkPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;
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
        switch (requestCode){
            case 1:
                if(grantResults.length > 0) {
                    for(int i = 0 ; i < grantResults.length ; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
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
        Snackbar.make(mainConstraint, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab1:
                break;

            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}
