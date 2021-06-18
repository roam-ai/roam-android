package com.example.roamexample;

import android.app.Application;

import com.roam.sdk.Roam;

public class MainApplication extends Application {
    // TODO: Step 2 : Initialize SDK
    @Override
    public void onCreate() {
        super.onCreate();
         Roam.initialize(this, "a13a4f2ed097203df42abd6c9261cde373bdef14cc4c9437705645700f490a11");
    }
}
