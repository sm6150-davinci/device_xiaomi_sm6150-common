/*
 * Copyright (C) 2025 The LineageOS Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package org.lineageos.settings.bypasschrg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {

    private static final String TAG = "PowerConnectionReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BypassChargingController controller = BypassChargingController.getInstance(context);

        if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
            if (controller.isBypassChargingEnabled()) {
                Log.i(TAG, "Power disconnected - disabling bypass charging");
                controller.disableBypassCharging();
            }
        }
    }
}

