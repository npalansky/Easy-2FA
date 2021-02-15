package com.app.AuthReceiverService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SmsProcessingService extends Service {
    private static final String CHANNEL_ID = "2FA Service";
    private static final int NOTIFICATION_ID = 8284; //random #
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String RESTART_SERVICE = "restartservice";

    BroadcastReceiver mReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(SMS_RECEIVED);
        mReceiver = new SmsBroadcastReceiver();
        registerReceiver(mReceiver, new IntentFilter());
    }

    public void onDestroy() {
        unregisterReceiver(mReceiver);

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(RESTART_SERVICE);
        broadcastIntent.setClass(this, AutoStartReceiver.class);
        this.sendBroadcast(broadcastIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification =
                new Notification.Builder(this, CHANNEL_ID)
                        .setOngoing(true)
                        .setContentTitle("Service Launched")
                        .setContentText("subtext")
                        .setContentIntent(pendingIntent)
                        .build();

        // Notification ID cannot be 0.
        startForeground(NOTIFICATION_ID, notification);
        return START_STICKY;
    }

}
