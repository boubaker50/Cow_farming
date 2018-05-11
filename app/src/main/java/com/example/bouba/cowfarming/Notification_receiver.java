package com.example.bouba.cowfarming;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by bouba on 01-Nov-17.
 */

public class Notification_receiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent rep = new Intent(context, MainActivity.class);
        rep.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pend = PendingIntent.getActivity(context, 100, rep, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pend)
                .setSmallIcon(R.drawable.splash)
                .setContentTitle("Cow farming")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("It's time to put your daily data in the system.\nHave a good day."))
                .setContentText("It's time to put your daily data in the system.\nHave a good day.")
                .setColor(Color.parseColor("#7f8727"))
                .setAutoCancel(true);
        notificationManager.notify(100, builder.build());
    }
}
