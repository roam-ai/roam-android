package com.example.roamexample;

import android.app.Application;

import com.roam.sdk.Roam;

public class MainApplication extends Application {
    // TODO: Step 2 : Initialize SDK
    @Override
    public void onCreate() {
        super.onCreate();
         Roam.initialize(this, "5cf6ce67cb5c75fd56941bea1fd514cdd62319eb1dd664ee0321c97e04f49b8f");
    }
}
