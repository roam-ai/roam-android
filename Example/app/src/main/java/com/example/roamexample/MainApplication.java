package com.example.roamexample;

import android.app.Application;

import com.roam.sdk.Roam;

public class MainApplication extends Application {
    // TODO: Step 2 : Initialize SDK
    @Override
    public void onCreate() {
        super.onCreate();
       // Roam.initialize(this, "YOUR-PUBLISH-KEY");
        Roam.initialize(this, "d1c42668d969affa61692b543b9c55bc6672abff3414da4e0c3db11a05d5a76f");
    }
}
