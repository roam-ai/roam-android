package com.example.roamexample;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.roamexample.ui.MainActivity;


public class NotificationHelper {
    private static final String ANDROID_CHANNEL_ID = "com.roam.example";
    public static final int NOTIFICATION_ID = 102;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name = context.getString(R.string.app_name);
        NotificationChannel channel = new NotificationChannel(ANDROID_CHANNEL_ID, name, NotificationManager.IMPORTANCE_LOW);
        channel.enableLights(false);
        channel.enableVibration(false);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        Intent intent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ANDROID_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_geospark)
                .setContentTitle("App is running")
                .setContentText("Click here to open the app")
                .setContentIntent(notificationPendingIntent);
        builder.setAutoCancel(true);
        return builder.build();
    }

    public static void cancelNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(NOTIFICATION_ID);
        }
    }
}
