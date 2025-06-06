/*
 * Copyright (C) 2025 The LineageOS Project
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

package org.lineageos.settings.bypasschrg;

import android.os.Bundle;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.TwoStatePreference;

import org.lineageos.settings.R;

public class BypassChargingSettings extends PreferenceFragment {

    private static final String KEY_BYPASS_CHARGING = "bypass_charging";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.bypass_charging_settings, rootKey);

        BypassChargingController bypassController =
                BypassChargingController.getInstance(getContext());
        boolean bypassSupported = bypassController.isBypassChargingSupported();

        TwoStatePreference bypassPreference = findPreference(KEY_BYPASS_CHARGING);
        bypassPreference.setEnabled(bypassSupported);
        if (bypassSupported) {
            bypassPreference.setChecked(bypassController.isBypassChargingEnabled());
            bypassPreference.setOnPreferenceChangeListener((pref, newValue) -> {
                bypassController.toggleBypassCharging((boolean) newValue);
                return true;
            });
        } else {
            bypassPreference.setSummary(R.string.bypass_charging_unavailable);
        }
    }
}
