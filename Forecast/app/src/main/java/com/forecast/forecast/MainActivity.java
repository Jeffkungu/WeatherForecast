package com.forecast.forecast;

import android.app.Dialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.forecast.forecast.presenter.MainPresenter;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity implements Contract.MainView, View.OnClickListener {
    private ConstraintLayout mainConstraint;
    private FloatingActionButton fab1;
    private Contract.MainViewEventlistener eventlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainConstraint = findViewById(R.id.mainConstraint);
        eventlistener = new MainPresenter(this, this);
        eventlistener.checkGoogleServiceAvailability();
    }

    @Override
    public void initialiseViews() {
        fab1 = findViewById(R.id.fab1);

        fab1.setOnClickListener(this);
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
}
