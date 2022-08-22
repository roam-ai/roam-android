package com.example.roamexample.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.roam.sdk.models.RoamError;
import com.roam.sdk.models.RoamLocation;
import com.roam.sdk.models.events.RoamEvent;
import com.roam.sdk.service.RoamReceiver;

import java.util.List;


public class LocationReceiver extends RoamReceiver {

    @Override
    public void onLocationUpdated(Context context, List<RoamLocation> list) {
        super.onLocationUpdated(context, list);
        Log.e("MyLocation", "Lat " + list.get(0).getLocation().getLatitude() + " Lng " + list.get(0).getLocation().getLongitude());
        //  Toast.makeText(context, "Location: "+"Lat: "+roamLocation.getLocation().getLatitude() +" Lng: "+roamLocation.getLocation().getLongitude(), Toast.LENGTH_SHORT).show();

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

