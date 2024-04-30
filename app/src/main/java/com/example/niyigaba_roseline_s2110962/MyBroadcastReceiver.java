package com.example.niyigaba_roseline_s2110962;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Check the action of the received intent
        if (intent.getAction() != null) {
            if (intent.getAction().equals("com.example.forecast1.MY_BROADCAST_ACTION")) {
                // Handle the custom broadcast action
                Log.d(TAG, "Custom broadcast received");
                // Perform actions or start activities as needed
            }
        }
    }
}
