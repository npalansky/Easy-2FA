package com.app.AuthReceiverService;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class SmsBroadcastReceiver extends android.content.BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {

            Bundle bundle = intent.getExtras();
            String message = digestMessage(bundle);
            if (message != null) {
                String parsedMessage = parseMessage(message);
                if (parsedMessage != null) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", parsedMessage);
                    clipboard.setPrimaryClip(clip);
                }
            }
        }
    }

    private String digestMessage (Bundle bundle) {
        if (bundle != null) {
            // get sms objects
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus.length == 0) {
                return null;
            }
            // large message might be broken into many
            SmsMessage[] messages = new SmsMessage[pdus.length];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sb.append(messages[i].getMessageBody());
            }
            String sender = messages[0].getOriginatingAddress();
            String message = sb.toString();

            return message;
        }
        return null;
    }

    private String parseMessage(String message) {
        if (message != null) {
            List<String> nums = Arrays.asList(message
                    .replaceAll("[^0-9]+", " ")
                    .trim()
                    .split(" ")
            );
            if (nums.size() > 0) {
                String code = nums.get(0); //return first number in the tex
                return code;
            }
        }
        return null;
    }
}
