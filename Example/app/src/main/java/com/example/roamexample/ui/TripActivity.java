package com.example.roamexample.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.roamexample.R;
import com.example.roamexample.adapter.TripAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.roam.sdk.Roam;
import com.roam.sdk.trips_v2.callback.RoamActiveTripsCallback;
import com.roam.sdk.trips_v2.models.Error;
import com.roam.sdk.trips_v2.models.RoamActiveTripsResponse;
import com.roam.sdk.trips_v2.models.Trips;

import java.util.List;

public class TripActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private TripAdapter adapter;
    private TextView snackBar;
    private boolean offline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        offline = getIntent().getBooleanExtra("OFFLINE", false);
        adapter = new TripAdapter(this);
        progressbar = findViewById(R.id.progressbar);
        snackBar = findViewById(R.id.snackBar);
        ImageView txtBack = findViewById(R.id.img_back);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        checkPermissions();
    }


    private void checkPermissions() {
        if (!Roam.checkLocationServices()) {
            Roam.requestLocationServices(this);
        } else if (!Roam.checkLocationPermission()) {
            Roam.requestLocationPermission(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Roam.checkBackgroundLocationPermission()) {
            Roam.requestBackgroundLocationPermission(this);
        } else {
            refreshList();
        }
    }

    private void show() {
        progressbar.setVisibility(View.VISIBLE);
    }

    private void hide() {
        progressbar.setVisibility(View.GONE);
    }

    public void refreshList() {
        show();
        Roam.getActiveTrips(offline, new RoamActiveTripsCallback() {
            @Override
            public void onSuccess(RoamActiveTripsResponse roamActiveTripsResponse) {
                hide();
                List<Trips> activeTrips = roamActiveTripsResponse.getTrips();
                if (activeTrips.size() != 0) {
                    activeTrips.get(0).getSyncStatus();
                    adapter.addList(activeTrips);
                }
            }

            @Override
            public void onError(Error error) {
                hide();
            }
        });
    }

    private void showMsg(String msg) {
        Snackbar.make(snackBar, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Roam.REQUEST_CODE_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showMsg("Location permission required");
                }
                break;
            case Roam.REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showMsg("Background Location permission required");
                }
                break;
            case Roam.REQUEST_CODE_STORAGE_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showMsg("Write storage permission required");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Roam.REQUEST_CODE_LOCATION_ENABLED) {
            refreshList();
        }
    }
}