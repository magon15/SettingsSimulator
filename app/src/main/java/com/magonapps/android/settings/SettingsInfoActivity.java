package com.magonapps.android.settings;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Gonzales on 9/23/2017.
 */

public class SettingsInfoActivity extends SettingsFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SettingsInfoFragment.newInstance();
    }
}
