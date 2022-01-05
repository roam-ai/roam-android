package com.example.roamexample;

import android.app.Application;

import com.roam.sdk.Roam;

public class MainApplication extends Application {
    // TODO: Step 2 : Initialize SDK
    @Override
    public void onCreate() {
        super.onCreate();
         Roam.initialize(this, "7c705251188daa2c20f6f17ad43571f5da1d0062a374ac2114db040288ce07c0");
    }
}
