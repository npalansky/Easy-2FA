package com.app.AuthReceiverService;

import android.accessibilityservice.AccessibilityService;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


public class PasteButtonReceiver extends AccessibilityService {
    private static final int VOLUME_UP = 24;
    private static final double PASTE_ACTIVE_SECONDS = 5.0; //paste will remain active for 5 seconds after receiving a code

    public static final String TAG = "@@paste button receiver";
    private AccessibilityNodeInfo nodeInfo;

    @Override
    public void onServiceConnected() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo nodeInfo = accessibilityEvent.getSource();
        if (nodeInfo != null) {
            this.nodeInfo = nodeInfo;
        }
    }

    @Override
    public void onInterrupt() {
    }

    private double getSecondsSinceLastCode() {
        long currTimestampMillis = System.currentTimeMillis();
        double secondsSinceLastCode = (currTimestampMillis - SmsBroadcastReceiver.LAST_CODE_TIMESTAMP_MILLIS) / 1000;
        return secondsSinceLastCode;
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == VOLUME_UP && event.getAction() == KeyEvent.ACTION_DOWN && this.nodeInfo != null && getSecondsSinceLastCode() < PASTE_ACTIVE_SECONDS) {
            //Paste in the text
            this.nodeInfo.refresh();
            this.nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            
            //Hide the keyboard
            this.nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS);

            return true;
        }

        return super.onKeyEvent(event);
    }
}
