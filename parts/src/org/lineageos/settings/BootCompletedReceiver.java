/*
 * Copyright (C) 2015 The CyanogenMod Project
 *               2017-2020 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.lineageos.settings.doze.DozeUtils;

import androidx.preference.PreferenceManager;
import org.lineageos.settings.gamebar.GameBar;
import org.lineageos.settings.gamebar.GameBarMonitorService;

public class BootCompletedReceiver extends BroadcastReceiver {
    private static final boolean DEBUG = true;
    private static final String TAG = "XiaomiParts";

    @Override
public void onReceive(final Context context, Intent intent) {
    String action = intent.getAction();

    if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
        if (DEBUG) Log.d(TAG, "Received boot completed intent");

        // Restore game bar preferences
        restoreGameBarOverlayState(context);
        if (DEBUG) Log.d(TAG, "Restoring GameBar overlay state");

        // Notify DozeUtils about boot completion
        DozeUtils.onBootCompleted(context);

    } else if (Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(action)) {
        if (DEBUG) Log.d(TAG, "Received locked boot completed intent");

        // Restore game bar preferences
        restoreGameBarOverlayState(context);
        if (DEBUG) Log.d(TAG, "Restoring GameBar overlay state");
    }
}

private void restoreGameBarOverlayState(Context context) {
    var prefs = PreferenceManager.getDefaultSharedPreferences(context);
    boolean mainEnabled = prefs.getBoolean("game_bar_enable", false);
    boolean autoEnabled = prefs.getBoolean("game_bar_auto_enable", false);

    if (mainEnabled) {
        GameBar.getInstance(context).applyPreferences();
        GameBar.getInstance(context).show();
    }

    if (autoEnabled) {
        // Start GameBarMonitorService
        Intent monitorIntent = new Intent(context, GameBarMonitorService.class);
        context.startService(monitorIntent);        }
    }
}
