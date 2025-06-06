/*
 * Copyright (C) 2025 kenway214
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

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import org.lineageos.settings.R;

public class BypassChargingTile extends TileService {


    private BypassChargingController mBypassController;
    private boolean mEnabled;

    @Override
    public void onCreate() {
        super.onCreate();
        mBypassController = BypassChargingController.getInstance(this);
    }

    @Override
    public void onStartListening() {
        mEnabled = mBypassController.isBypassChargingEnabled();
        updateTileState();
    }

    @Override
    public void onClick() {
        if (mEnabled == mBypassController.isBypassChargingEnabled()) {
            mEnabled = !mEnabled;
            updateTileState();
            mBypassController.toggleBypassCharging(mEnabled);
        }
    }

    private void updateTileState() {
        Tile tile = getQsTile();
        if (tile == null) return;

        tile.setState(mEnabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.setLabel(getString(R.string.bypass_charging_title));
        tile.setContentDescription(getString(R.string.bypass_charging_summary));
        tile.updateTile();
    }
}
