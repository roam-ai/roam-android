package com.example.roamexample.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roamexample.R;
import com.example.roamexample.service.ForegroundService;
import com.example.roamexample.storage.RoamPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.roam.sdk.Roam;
import com.roam.sdk.RoamPublish;
import com.roam.sdk.RoamTrackingMode;
import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.callback.RoamCreateTripCallback;
import com.roam.sdk.callback.RoamLogoutCallback;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamUser;
import com.roam.sdk.models.createtrip.RoamCreateTrip;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ProgressBar progressBar;
    private RadioGroup mRadioGroup;
    private EditText edtAccuracyFilter, edtTime, edtDist;
    private TextView snackBar;
    private CheckBox ckOffline, ckFilter, ckMock, ckToggleEvents, ckToggleLocation;
    private Button btnStartTracking, btnStopTracking, btnCustomAccuracyEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Roam.disableBatteryOptimization();

        progressBar = findViewById(R.id.progressbar);
        mRadioGroup = findViewById(R.id.radioGroup);
        btnStartTracking = findViewById(R.id.btnStartTracking);
        btnStopTracking = findViewById(R.id.btnStopTracking);
        btnCustomAccuracyEnable = findViewById(R.id.btnCustomAccuracyEnable);
        edtAccuracyFilter = findViewById(R.id.edtAccuracyFilter);
        edtTime = findViewById(R.id.edtTime);
        edtDist = findViewById(R.id.edtDist);
        snackBar = findViewById(R.id.snackBar);
        ckOffline = findViewById(R.id.ckOffline);
        Button btnCreateTrip = findViewById(R.id.btnCreateTrip);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnTrip = findViewById(R.id.btnTrip);

        ckMock = findViewById(R.id.ckMock);
        ckFilter = findViewById(R.id.ckFilter);
        ckToggleEvents = findViewById(R.id.ckToggleEvents);
        ckToggleLocation = findViewById(R.id.ckToggleLocation);
        btnStartTracking.setOnClickListener(this);
        btnStopTracking.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnTrip.setOnClickListener(this);
        btnCreateTrip.setOnClickListener(this);
        btnCustomAccuracyEnable.setOnClickListener(this);
        ckMock.setOnCheckedChangeListener(this);
        ckFilter.setOnCheckedChangeListener(this);
        ckToggleEvents.setOnCheckedChangeListener(this);
        ckToggleLocation.setOnCheckedChangeListener(this);

        checkPermissions();



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
            case R.id.btnCustomAccuracyEnable:
                if (!TextUtils.isEmpty(edtAccuracyFilter.getText().toString())) {
                    Roam.enableAccuracyEngine(Integer.parseInt(edtAccuracyFilter.getText().toString()));
                } else {
                    showMsg("Enter accuracy");
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {

            case R.id.ckMock:
                if (isChecked) {
                    Roam.allowMockLocation(true);
                } else {
                    Roam.allowMockLocation(false);
                }
                break;

            case R.id.ckFilter:
                if (isChecked) {
                    Roam.enableAccuracyEngine();
                } else {
                    Roam.disableAccuracyEngine();
                }
                break;

            case R.id.ckToggleLocation:
                if (isChecked) {
                    // TODO: Step 6 : Subscribe to your userId to listen location updated from LocationReceiver.java
                    Roam.subscribe(Roam.Subscribe.LOCATION, RoamPreferences.getUserId(MainActivity.this, "userId"));
                } else {
                    Roam.unSubscribe(Roam.Subscribe.LOCATION, RoamPreferences.getUserId(MainActivity.this, "userId"));
                }
                break;

            case R.id.ckToggleEvents:
                if (isChecked) {
                    // TODO: Step 7 : Publish and save location in Roam Backend.
                    RoamPublish geoSparkPublish = new RoamPublish.Builder()
                            .build();
                    Roam.publishAndSave(geoSparkPublish);
                } else {
                    Roam.unSubscribe(Roam.Subscribe.EVENTS, RoamPreferences.getUserId(MainActivity.this, "userId"));
                }
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
           // startTracking();
            callBottomDialoge();
        }
    }

    // TODO: Step 9 : Choose tracking mode and  Start tracking.
    private void startTracking() {
        int selectedId = mRadioGroup.getCheckedRadioButtonId();

        publishAndSubsribe();

        if (selectedId == R.id.rbOption4 && edtTime.getText().toString().equalsIgnoreCase(""))
            showMsg("Enter time interval");
        else if (selectedId == R.id.rbOption5 && edtDist.getText().toString().equalsIgnoreCase(""))
            showMsg("Enter distance interval");
        else if (selectedId == R.id.rbOption1) {
            Roam.startTracking(RoamTrackingMode.ACTIVE);
            trackingStatus();
        } else if (selectedId == R.id.rbOption2) {
            Roam.startTracking(RoamTrackingMode.BALANCED);
            trackingStatus();
        } else if (selectedId == R.id.rbOption3) {
            Roam.startTracking(RoamTrackingMode.PASSIVE);
            trackingStatus();
        } else if (selectedId == R.id.rbOption4) {
            RoamTrackingMode roamTrackingMode = new RoamTrackingMode.Builder(Integer.parseInt(edtTime.getText().toString())) //5
                    .setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH)
                    .build();
            Roam.startTracking(roamTrackingMode);
            trackingStatus();
        } else if (selectedId == R.id.rbOption5) {
            RoamTrackingMode roamTrackingMode = new RoamTrackingMode.Builder(Integer.parseInt(edtTime.getText().toString()), 60) //100
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
        Roam.createTrip(null, null, ckOffline.isChecked(), null, new RoamCreateTripCallback() {
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

    private void showMsg(String msg) {
        Snackbar.make(snackBar, msg, Snackbar.LENGTH_SHORT).show();
    }

    private void callBottomDialoge() {
        BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        dialog.setContentView(R.layout.time_tracking_layout);

        TextView tvPassive=dialog.findViewById(R.id.tvPassive);
        TextView tvActive=dialog.findViewById(R.id.tvActive);
        TextView tvTime=dialog.findViewById(R.id.tvTime);
        TextView tvBalanced=dialog.findViewById(R.id.tvBalanced);
        TextView tvDistance=dialog.findViewById(R.id.tvDistance);

        LinearLayout llTime=dialog.findViewById(R.id.llTime);
        LinearLayout llDistance=dialog.findViewById(R.id.llDistance);

        EditText edtTime=dialog.findViewById(R.id.edtTime);
        EditText edtDist=dialog.findViewById(R.id.edtDist);

        TextView tvTimeContinue=dialog.findViewById(R.id.tvTimeContinue);
        TextView tvDistContinue=dialog.findViewById(R.id.tvDistContinue);

        tvActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roam.startTracking(RoamTrackingMode.ACTIVE);
                trackingStatus();
                dialog.cancel();
            }
        });

        tvPassive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roam.startTracking(RoamTrackingMode.PASSIVE);
                trackingStatus();
                dialog.cancel();
            }
        });

        tvBalanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roam.startTracking(RoamTrackingMode.BALANCED);
                trackingStatus();
                dialog.cancel();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llTime.setVisibility(View.VISIBLE);
            }
        });

        tvDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llDistance.setVisibility(View.VISIBLE);
            }
        });



        tvTimeContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTime.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(MainActivity.this, "Please enter time interval", Toast.LENGTH_SHORT).show();
                else
                {
                    RoamTrackingMode roamTrackingMode = new RoamTrackingMode.Builder(Integer.parseInt(edtTime.getText().toString())) //5
                            .setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH)
                            .build();
                    Roam.startTracking(roamTrackingMode);
                    trackingStatus();
                    dialog.cancel();
                }

            }
        });

        tvDistContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtDist.getText().toString().equalsIgnoreCase(""))
                    Toast.makeText(MainActivity.this, "Please enter distance interval", Toast.LENGTH_SHORT).show();
                else {
                    RoamTrackingMode roamTrackingMode = new RoamTrackingMode.Builder(Integer.parseInt(edtDist.getText().toString()), 60) //100
                            .setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH)
                            .build();
                    Roam.startTracking(roamTrackingMode);
                    trackingStatus();
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }
}