package com.example.roamexample.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.roamexample.R;
import com.example.roamexample.service.LocationReceiver;
import com.example.roamexample.storage.RoamPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.roam.sdk.Roam;
import com.roam.sdk.RoamPublish;
import com.roam.sdk.callback.RoamCallback;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView snackBar;
    private ProgressBar progressBar;
    private EditText edtDescription, edtGetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }

    private void initialize() {
        if (RoamPreferences.isSignedIn(this)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_login);
            progressBar = findViewById(R.id.progressbar);
            snackBar = findViewById(R.id.snackBar);
            edtDescription = findViewById(R.id.edtDescription);
            edtGetUser = findViewById(R.id.edtUserId);
            Button btnCreateUser = findViewById(R.id.btnCreateUser);
            Button btnGetUser = findViewById(R.id.btnGetUser);
            btnCreateUser.setOnClickListener(this);
            btnGetUser.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateUser:
                createUser();
                break;

            case R.id.btnGetUser:
                getUser();
                break;
        }
    }



    private void createUser() {
        show();
        // TODO: Step 3 : Create user
        Roam.createUser(edtDescription.getText().toString(),null, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                Log.e("userId ", roamUser.getUserId());
                RoamPreferences.setUserId(LoginActivity.this, roamUser.getUserId());
                toggleEvents();
            }

            @Override
            public void onFailure(RoamError roamError) {
                hide();
                showMsg(roamError.getMessage());
            }
        });
    }

    private void getUser() {
        String text = edtGetUser.getText().toString();
        if (TextUtils.isEmpty(text)) {
            showMsg("Enter userId");
        } else {
            show();
            // TODO: Step 3 : If already user created pass Roam userId
            Roam.getUser(text, new RoamCallback() {
                @Override
                public void onSuccess(RoamUser roamUser) {
                    //Log.e("Roam UserId ", roamUser.getUserId());
                    RoamPreferences.setUserId(LoginActivity.this, roamUser.getUserId());
                    hide();
                    toggleEvents();
                }

                @Override
                public void onFailure(RoamError error) {
                    hide();
                    showMsg(error.getMessage());
                }
            });
        }
    }

    private void toggleEvents() {
        // TODO: Step 4 : Toggle events
        Roam.toggleEvents(true, true, true, true, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                toggleListener();
                Log.e("TAG", "toggleEvents:called" );
            }

            @Override
            public void onFailure(RoamError roamError) {
                hide();
                showMsg(roamError.getMessage());
            }
        });
    }

    private void toggleListener() {
        // TODO: Step 5 : Toggle listener
        Roam.toggleListener(true, true, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {

                signedIn();
            }

            @Override
            public void onFailure(RoamError roamError) {
                hide();
                showMsg(roamError.getMessage());
                Log.e("TAG", "toggleListenerError: "+  "called");
            }
        });
    }

    private void signedIn() {
        RoamPreferences.setSignIn(LoginActivity.this, true);
        startActivity(new Intent(getApplicationContext(), ToggleActivity.class));
        finish();
    }

    private void show() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hide() {
        progressBar.setVisibility(View.GONE);
    }

    private void showMsg(String msg) {
        Snackbar.make(snackBar, msg, Snackbar.LENGTH_SHORT).show();
    }
}