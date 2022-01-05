package com.example.roamexample.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.roamexample.R;
import com.example.roamexample.storage.RoamPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.roam.sdk.Roam;
import com.roam.sdk.RoamPublish;
import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamUser;

public class ToggleActivity extends AppCompatActivity implements View.OnClickListener {

    private SwitchCompat swLocListener;
    private SwitchCompat swEventListener;
    private SwitchCompat swSubscribeLoc;
    private SwitchCompat swSubscribeEvents;
    private SwitchCompat swNotification;

    private TextView btnNext;

    private Boolean locationListener = false;
    private Boolean EventListener = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);

        initialize();
    }

    private void initialize() {
        swLocListener = findViewById(R.id.swLocListener);
        swEventListener = findViewById(R.id.swEventListener);
        swSubscribeLoc = findViewById(R.id.swSubscribeLoc);
        swSubscribeEvents = findViewById(R.id.swSubscribeEvents);
        swNotification = findViewById(R.id.swNotification);

        btnNext = findViewById(R.id.btnNext);

        swLocListener.setOnClickListener(ToggleActivity.this);
        swEventListener.setOnClickListener(ToggleActivity.this);
        swSubscribeLoc.setOnClickListener(ToggleActivity.this);
        swSubscribeEvents.setOnClickListener(ToggleActivity.this);
        swNotification.setOnClickListener(ToggleActivity.this);
        btnNext.setOnClickListener(ToggleActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.swLocListener:
                if (swLocListener.isChecked()) {
                    locationListener = true;
                    toggleListener();
                } else {
                    locationListener = false;
                    toggleListener();
                }
                break;

            case R.id.swEventListener:
                if (swEventListener.isChecked()) {
                    EventListener = true;
                    toggleListener();
                } else {
                    EventListener = false;
                    toggleListener();
                }
                break;
            case R.id.swSubscribeLoc:
                if (swSubscribeLoc.isChecked()) {
                    Roam.subscribe(Roam.Subscribe.LOCATION, RoamPreferences.getUserId(ToggleActivity.this, "userId"));
                } else {
                    Roam.unSubscribe(Roam.Subscribe.LOCATION, RoamPreferences.getUserId(ToggleActivity.this, "userId"));
                }
                break;
            case R.id.swSubscribeEvents:
                if (swSubscribeEvents.isChecked()) {
                    RoamPublish geoSparkPublish = new RoamPublish.Builder()
                            .build();
                    Roam.publishAndSave(geoSparkPublish);
                    Roam.subscribe(Roam.Subscribe.EVENTS, RoamPreferences.getUserId(ToggleActivity.this, "userId"));
                } else {
                    Roam.unSubscribe(Roam.Subscribe.EVENTS, RoamPreferences.getUserId(ToggleActivity.this, "userId"));
                }
                break;



            case R.id.swNotification:
                if (swNotification.isChecked()) {
                    Roam.setForegroundNotification(true,"Roam Example","Click here to redirect the app",
                            R.drawable.ic_geospark,"com.example.roamexample.ui.MainActivity");
                } else {
                    Roam.setForegroundNotification(false,"Roam Example","Click here to redirect the app",
                            R.drawable.ic_geospark,"com.example.roamexample.ui.MainActivity");                }
                break;

            case R.id.btnNext:
                homeIntent();
                break;
        }
    }


    private void toggleListener() {
        Roam.toggleListener(locationListener, EventListener, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                Log.e("TAG", "ToggleSuccess: ");
            }

            @Override
            public void onFailure(RoamError roamError) {
                Log.e("TAG", roamError.getMessage() );
            }
        });
    }

    private void homeIntent() {
       // RoamPreferences.setSignIn(ToggleActivity.this, true);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}