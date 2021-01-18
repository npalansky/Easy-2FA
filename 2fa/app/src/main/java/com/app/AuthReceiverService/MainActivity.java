package com.app.AuthReceiverService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String RESTART_SERVICE = "restartservice";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent launchIntent = new Intent(getApplicationContext(), SmsProcessingService.class);
        getApplicationContext().startForegroundService(launchIntent);
    }

    @Override
    protected void onDestroy() {
        //stopService(mServiceIntent);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(RESTART_SERVICE);
        broadcastIntent.setClass(this, AutoStartReceiver.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }
}