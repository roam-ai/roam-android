package com.example.roamexample;

import android.app.Application;

import com.roam.sdk.Roam;

public class MainApplication extends Application {
    // TODO: Step 2 : Initialize SDK
    @Override
    public void onCreate() {
        super.onCreate();
        // Roam.initialize(this, "YOUR-PUBLISHABLE-KEY");
        Roam.initialize(this, "ca2beca8b0c0135a3b29957d8dcb252dbb9eb2d45f83523e94df3c24eb2ec2d7");
    }
}
