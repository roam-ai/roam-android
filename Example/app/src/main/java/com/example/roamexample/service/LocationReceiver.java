package com.example.roamexample.service;

import android.content.Context;
import android.util.Log;

import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamLocation;
import com.roam.sdk.service.RoamReceiver;


public class LocationReceiver extends RoamReceiver {

    @Override
    public void onLocationUpdated(Context context, RoamLocation roamLocation) {
        super.onLocationUpdated(context, roamLocation);
        Log.e("Location", "Lat " + roamLocation.getLocation().getLatitude() + " Lng " + roamLocation.getLocation().getLongitude());
    }


    @Override
    public void onError(Context context, RoamError roamError) {
        Log.e("onLocationUpdated", roamError.getMessage());
    }

}

