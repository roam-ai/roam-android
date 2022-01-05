package com.example.roamexample.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamLocation;
import com.roam.sdk.models.events.RoamEvent;
import com.roam.sdk.service.RoamReceiver;


public class LocationReceiver extends RoamReceiver {

    @Override
    public void onLocationUpdated(Context context, RoamLocation roamLocation) {
        super.onLocationUpdated(context, roamLocation);
        Log.e("Location", "Lat " + roamLocation.getLocation().getLatitude() + " Lng " + roamLocation.getLocation().getLongitude());
        Toast.makeText(context, "Location: "+"Lat: "+roamLocation.getLocation().getLatitude() +" Lng: "+roamLocation.getLocation().getLongitude(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onEventReceived(Context context, RoamEvent roamEvent) {
        super.onEventReceived(context, roamEvent);
        Log.e("Event", "Geofence Id:  " + roamEvent.getGeofence_id() + " Event Type:  " + roamEvent.getEvent_type());
    }


    @Override
    public void onError(Context context, RoamError roamError) {
        Log.e("onLocationUpdated", roamError.getMessage());
    }

}

