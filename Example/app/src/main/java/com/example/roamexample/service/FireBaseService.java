package com.example.roamexample.service;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.roamexample.R;
import com.example.roamexample.ui.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.roam.sdk.Roam;

import java.util.Random;


public class FireBaseService extends FirebaseMessagingService {

    public static final String CHANNEL_ID = "geospark_ignite_channel";

   @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
        //Add your token in your sharepreferences.
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try {
            showNotification(getApplicationContext(), remoteMessage);
        } catch (Exception e) {
        }
    }

    private void showNotification(Context context, RemoteMessage remoteMessage) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = context.getString(R.string.app_name);
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra(Roam.EXTRA, Roam.notificationReceiveHandler(remoteMessage.getData()));
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(intent);
            PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_geospark)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_geospark))
                    .setContentTitle("Campaign")
                    .setContentText(remoteMessage.getData().get("message"))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("message")))
                    .setContentIntent(notificationPendingIntent);
            builder.setAutoCancel(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(CHANNEL_ID);
            }
            builder.setAutoCancel(true);
            notificationManager.notify(getID(), builder.build());
        } catch (Exception e) {
        }
    }

    public static int getID() {
        return new Random().nextInt(10000);
    }

}
