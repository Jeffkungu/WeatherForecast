package com.forecast.forecast.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.forecast.forecast.Contract;
import com.forecast.forecast.R;
import com.forecast.forecast.adapter.BookmarkedLocationsAdapter;
import com.forecast.forecast.database.WeatherTable;
import com.forecast.forecast.presenter.BookmarkedLocationsPresenter;

import java.util.List;

public class BookmarkedLocationsActivity extends AppCompatActivity implements Contract.BookmarkedLocationsView, View.OnClickListener {
    private RecyclerView recViewLocations;
    private Contract.BookmarkedLocationsEventListener eventListener;
    private FloatingActionButton fabDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_locations);

        eventListener = new BookmarkedLocationsPresenter(this, this);
        eventListener.displayLocations();
        fabDelete = findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(this);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void populateRecyclerView(List<WeatherTable> weatherTables) {
        if (weatherTables.isEmpty()) {
            displayMessage(getString(R.string.no_bookmarked_locations));
        } else {
            BookmarkedLocationsAdapter locationsAdapter = new BookmarkedLocationsAdapter(weatherTables, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recViewLocations = findViewById(R.id.recViewLocations);
            recViewLocations.setLayoutManager(layoutManager);
            recViewLocations.setAdapter(locationsAdapter);
        }
    }

    @Override
    public void openMapPage() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabDelete:
                eventListener.deleteLocations();
                break;

            default:
                break;
        }
    }
}
