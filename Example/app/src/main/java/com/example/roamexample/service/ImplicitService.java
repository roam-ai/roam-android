package com.example.roamexample.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;


public class ImplicitService extends Service {
    private LocationReceiver mLocationReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        register();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegister();
    }

    private void register() {

        mLocationReceiver = new LocationReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.roam.android.RECEIVED");
        intentFilter.addAction("com.roam.android.NETWORK");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(mLocationReceiver, intentFilter, RECEIVER_EXPORTED);
        }else{
            registerReceiver(mLocationReceiver, intentFilter);
        }

    }

    private void unRegister() {
        if (mLocationReceiver != null) {
            unregisterReceiver(mLocationReceiver);
        }
    }
}