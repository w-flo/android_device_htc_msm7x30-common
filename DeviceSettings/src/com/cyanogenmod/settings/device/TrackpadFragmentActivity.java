/*
 * Copyright (C) 2012-2013 The CyanogenMod Project
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

package com.cyanogenmod.settings.device;

import android.content.res.Configuration;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.provider.Settings;

import com.cyanogenmod.settings.device.R;

public class TrackpadFragmentActivity extends PreferenceFragment {

    private static final String TAG = "DeviceSettings_Trackpad";
    private static final String TRACKBALL_WAKE_TOGGLE = "pref_trackball_wake_toggle";
    private static final String TRACKBALL_UNLOCK_TOGGLE = "pref_trackball_unlock_toggle";
    private static final String QUICK_LAUNCH = "pref_quick_launch";

    private static boolean sTrackball;
    private CheckBoxPreference mTrackballWake;
    private CheckBoxPreference mTrackballUnlockScreen;
    private Preference mQuickLaunch;
    private PreferenceScreen mPrefSet;
    private ContentResolver mCr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();
        sTrackball = res.getBoolean(R.bool.has_trackball);
        mCr = getActivity().getContentResolver();
        
        addPreferencesFromResource(R.xml.trackball_preferences);

        if (sTrackball) {

            mTrackballWake = (CheckBoxPreference) findPreference(TRACKBALL_WAKE_TOGGLE);
            mTrackballWake.setChecked(Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.TRACKBALL_WAKE_SCREEN, 1) == 1);
            mTrackballUnlockScreen = (CheckBoxPreference) findPreference(TRACKBALL_UNLOCK_TOGGLE);
            mTrackballUnlockScreen.setChecked(Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.TRACKBALL_UNLOCK_SCREEN, 0) == 1);
        }

        mQuickLaunch = (Preference) findPreference(QUICK_LAUNCH);
        if (getResources().getConfiguration().keyboard != Configuration.KEYBOARD_QWERTY) {
        getPreferenceScreen().removePreference(mQuickLaunch);
        }

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        String boxValue;
        String key = preference.getKey();

        if (preference == mTrackballWake) {
            Settings.System.putInt(mCr, Settings.System.TRACKBALL_WAKE_SCREEN, mTrackballWake.isChecked() ? 1 : 0);
        }
        else if (preference == mTrackballUnlockScreen) {
            Settings.System.putInt(mCr, Settings.System.TRACKBALL_UNLOCK_SCREEN, mTrackballUnlockScreen.isChecked() ? 1 : 0);
        }
        else {
     return false;
          }
        Log.w(TAG, "key: " + key);
    return true;
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

    }
}
