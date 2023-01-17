package com.example.mynotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class myNotification {
    final String CHANNEL_ID = "myNotification";
    final String CHANNEL_NAME = "notification_news";
    final String CHANNEL_DESCRIPTION = "push new notification news";
    private static myNotification instance = null;
    public  myNotification() {

    }
    public static myNotification getInstance() {
        if (instance == null) {
            instance = new myNotification();
        }
        return instance;
    }
    public void pushNotification(Context ctx, String title, String message, int notificationId, int notificationCount) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(ctx, MainActivity.class);
        intent.putExtra("Notification", "PUSH_NEW_FRAGMENT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setNumber(notificationCount)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        // push
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }
    public void createNotificationChannel(Context ctx) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
