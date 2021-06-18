package com.example.roamexample.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.roamexample.R;
import com.example.roamexample.service.ForegroundService;
import com.example.roamexample.storage.RoamPreferences;
import com.roam.sdk.Roam;
import com.roam.sdk.RoamPublish;
import com.roam.sdk.RoamTrackingMode;
import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.callback.RoamCreateTripCallback;
import com.roam.sdk.callback.RoamLogoutCallback;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamUser;
import com.roam.sdk.models.createtrip.RoamCreateTrip;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progressBar;
    private RadioGroup mRadioGroup;
    private CheckBox ckOffline;
    private Button btnStartTracking, btnStopTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Roam.disableBatteryOptimization();

        progressBar = findViewById(R.id.progressbar);
        mRadioGroup = findViewById(R.id.radioGroup);
        btnStartTracking = findViewById(R.id.btnStartTracking);
        btnStopTracking = findViewById(R.id.btnStopTracking);
        ckOffline = findViewById(R.id.ckOffline);
        Button btnCreateTrip = findViewById(R.id.btnCreateTrip);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnTrip = findViewById(R.id.btnTrip);

        btnStartTracking.setOnClickListener(this);
        btnStopTracking.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnTrip.setOnClickListener(this);
        btnCreateTrip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartTracking:
                checkPermissions();
                break;
            case R.id.btnStopTracking:
                stopTracking();
                break;
            case R.id.btnCreateTrip:
                createTrip();
                break;
            case R.id.btnTrip:
                startActivity(new Intent(this, TripActivity.class).putExtra("OFFLINE", ckOffline.isChecked()));
                break;
            case R.id.btnLogout:
                logout();
                break;
        }
    }

    // TODO: Step 8 : Grant permission.
    private void checkPermissions() {
        if (!Roam.checkLocationServices()) {
            Roam.requestLocationServices(this);
        } else if (!Roam.checkLocationPermission()) {
            Roam.requestLocationPermission(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Roam.checkBackgroundLocationPermission()) {
            Roam.requestBackgroundLocationPermission(this);
        } else {
            startTracking();
        }
    }

    // TODO: Step 9 : Choose tracking mode and  Start tracking.
    private void startTracking() {
        int selectedId = mRadioGroup.getCheckedRadioButtonId();
        publishAndSubsribe();
        if (selectedId == R.id.rbOption1) {
            Roam.startTracking(RoamTrackingMode.ACTIVE);
            trackingStatus();
        } else if (selectedId == R.id.rbOption2) {
            Roam.startTracking(RoamTrackingMode.BALANCED);
            trackingStatus();
        } else if (selectedId == R.id.rbOption3) {
            Roam.startTracking(RoamTrackingMode.PASSIVE);
            trackingStatus();
        } else if (selectedId == R.id.rbOption4) {
            RoamTrackingMode roamTrackingMode = new RoamTrackingMode.Builder(5)
                    .setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH)
                    .build();
            Roam.startTracking(roamTrackingMode);
            trackingStatus();
        } else if (selectedId == R.id.rbOption5) {
            RoamTrackingMode roamTrackingMode = new RoamTrackingMode.Builder(100, 60)
                    .setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH)
                    .build();
            Roam.startTracking(roamTrackingMode);
            trackingStatus();
        } else {
            Toast.makeText(this, "Select tracking options", Toast.LENGTH_SHORT).show();
        }
    }

    private void publishAndSubsribe() {
        Roam.toggleListener(true, true, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                Roam.subscribe(Roam.Subscribe.LOCATION, roamUser.getUserId());
                RoamPublish geoSparkPublish = new RoamPublish.Builder()
                        .build();
                Roam.publishAndSave(geoSparkPublish);
            }

            @Override
            public void onFailure(RoamError roamError) {

            }
        });
    }

    // TODO: Step 10 : Stop tracking
    private void stopTracking() {
        Roam.stopTracking();
        trackingStatus();
    }

    private void createTrip() {
        show();
        Roam.createTrip(null, null, ckOffline.isChecked(), new RoamCreateTripCallback() {
            @Override
            public void onSuccess(RoamCreateTrip geoSparkCreateTrip) {
                hide();
            }

            @Override
            public void onFailure(RoamError status) {
                hide();
            }
        });
    }

    private void trackingStatus() {
        if (Roam.isLocationTracking()) {
            startService(new Intent(this, ForegroundService.class));
            btnStartTracking.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
            btnStopTracking.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
            btnStartTracking.setEnabled(false);
            btnStopTracking.setEnabled(true);
        } else {
            stopService(new Intent(this, ForegroundService.class));
            btnStartTracking.setBackground(getResources().getDrawable(R.drawable.bg_button_enable));
            btnStopTracking.setBackground(getResources().getDrawable(R.drawable.bg_button_disable));
            btnStartTracking.setEnabled(true);
            btnStopTracking.setEnabled(false);
        }
    }

    private void logout() {
        Roam.logout(new RoamLogoutCallback() {
            @Override
            public void onSuccess(String message) {
                RoamPreferences.setSignIn(getApplicationContext(), false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }

            @Override
            public void onFailure(RoamError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void show() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hide() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Roam.REQUEST_CODE_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Location permission required", Toast.LENGTH_SHORT).show();
                }
                break;
            case Roam.REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Background Location permission required", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Roam.REQUEST_CODE_LOCATION_ENABLED) {
            checkPermissions();
        }
    }
}