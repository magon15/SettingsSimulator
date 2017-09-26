package com.magonapps.android.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Gonzales on 9/23/2017.
 */

public class SettingsBroadcastFragment extends Fragment {

    private static final String TAG = "SetBroadcastFragment";

    private BroadcastReceiver mBroadcastReceiverParent;

    public BroadcastReceiver getBroadcastReceiverParent() {
        return mBroadcastReceiverParent;
    }

    public void setBroadcastReceiverParent(BroadcastReceiver broadcastReceiverParent) {
        mBroadcastReceiverParent = broadcastReceiverParent;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();{
            filter.addAction(Intent.ACTION_HEADSET_PLUG);
            filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            filter.addAction(Intent.ACTION_SCREEN_ON);
        }
        getActivity().registerReceiver(mBroadcastReceiverParent, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mBroadcastReceiverParent);
    }


}
