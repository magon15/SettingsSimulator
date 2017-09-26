package com.magonapps.android.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Gonzales on 9/21/2017.
 */

public class SettingsPreferences {

    public static final String[] PREF_SETTINGS_LIST =
            {"screen_on",
             "airplane_mode_on",
             "headset_plugged"};

    private static boolean[] mSettingsPreferences;
    private static boolean[] isBroadCastTriggered;

    private static int powerCount;

    public static void setSettingsPreferences(Context context) {
        mSettingsPreferences = new boolean[PREF_SETTINGS_LIST.length];
        for(int i = 0; i < SettingsPreferences.PREF_SETTINGS_LIST.length; i++){
            mSettingsPreferences[i] = SettingsPreferences.getStoredSettings(
                    SettingsPreferences.PREF_SETTINGS_LIST[i],
                    context
            );
        }
    }

    public static boolean getStoredSettings(String settingsId, Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(settingsId, false);
    }

    public static void setSettingsList(String settingsId, Context context, boolean isOn){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(settingsId, isOn)
            .apply();

        setSettingsPreferences(context);
    }

    public static int getPowerCount() {
        return powerCount;
    }

    public static void setPowerCount(int powerCount) {
        SettingsPreferences.powerCount = powerCount;
    }

    public static boolean[] getIsBroadCastTriggered() {
        return isBroadCastTriggered;
    }

    public static void setIsBroadCastTriggered(boolean[] isBroadCastTriggered) {
        isBroadCastTriggered[0]=true;
        SettingsPreferences.isBroadCastTriggered = isBroadCastTriggered;
    }

    public static boolean[] getSettingsPreferences(Activity activity) {
        return mSettingsPreferences;
    }
}
