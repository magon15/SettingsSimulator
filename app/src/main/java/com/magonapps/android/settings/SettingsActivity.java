package com.magonapps.android.settings;

import android.support.v4.app.Fragment;

public class SettingsActivity extends SettingsFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SettingsFragment.newInstance();
    }



}
