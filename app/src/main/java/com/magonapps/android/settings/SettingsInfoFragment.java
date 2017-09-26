package com.magonapps.android.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Gonzales on 9/22/2017.
 */

public class SettingsInfoFragment extends SettingsBroadcastFragment implements SettingsPagerActivity.PagerCallback{

    private RecyclerView mSettingsInfoRecyclerView;
    private RecyclerView.Adapter mSettingsInfoAdapter;

    private BroadcastReceiver mBroadcastReceiver;


    private static final int[] imageIdOff = {
            R.drawable.off,
            R.drawable.airplane_mode_off,
            R.drawable.headset_unplugged
    };

    private static final int[] imageIdOn = {
            R.drawable.on,
            R.drawable.airplane_mode_on,
            R.drawable.headset_plugged
    };

    private String[] infoTitle;
    private String[] infoDesc;


    public static Fragment newInstance(){
        return new SettingsInfoFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoTitle=getResources().getStringArray(R.array.settings_info_title);
        checkOnOffStates();

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean[] isBroadCastTriggered = SettingsPreferences.getIsBroadCastTriggered();

                if (intent.getAction() == Intent.ACTION_SCREEN_ON) {
                    SettingsPreferences.setPowerCount(SettingsPreferences.getPowerCount() + 1);
                    //power icon immediately set to blue once you ticked the Power on broadcast checkbox
                    isBroadCastTriggered[0] = true;
                } else if (intent.getAction() == Intent.ACTION_HEADSET_PLUG) {
                    isBroadCastTriggered[1]=checkAirplaneModeState();
                } else if (intent.getAction() == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    isBroadCastTriggered[2]=checkHeadsetState();
                }

                SettingsPreferences.setIsBroadCastTriggered(isBroadCastTriggered);

                updateInfo();
            }
        };

        //sets the BroadcastReceiver in superclass which is SettingsBroadcastFragment
        setBroadcastReceiverParent(mBroadcastReceiver);

    }

    public boolean checkAirplaneModeState(){
        boolean isAirplaneModeOn;

        if (Settings.System.getInt(getActivity().getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0) {
            isAirplaneModeOn = true;
        } else isAirplaneModeOn = false;

        return isAirplaneModeOn;
    }

    public boolean checkHeadsetState(){
        boolean isHeadsetConnected;

        AudioManager mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        if (mAudioManager.isWiredHeadsetOn()) {
            isHeadsetConnected= true;
        } else isHeadsetConnected = false;

        return isHeadsetConnected;
    }

    public void checkOnOffStates(){

        //check if airplane mode is enabled or headset is plugged
        boolean[] isBroadCastTriggered=new boolean[SettingsPreferences.PREF_SETTINGS_LIST.length];
        infoDesc=new String[SettingsPreferences.PREF_SETTINGS_LIST.length];

        for(int i = 0; i < SettingsPreferences.PREF_SETTINGS_LIST.length; i++) {
            infoDesc[i] = getResources().getStringArray(R.array.settings_info_desc)[i];
            if (SettingsPreferences.getStoredSettings(SettingsPreferences.PREF_SETTINGS_LIST[i], getActivity())) {
                if(i==0){
                    //sets the String value of the amount of times that you turned on the device while the app is running on foreground.
                    infoDesc[0] = infoDesc[0].replace("%1$s", Integer.toString(SettingsPreferences.getPowerCount()));
                }else if(i==1) {
                    isBroadCastTriggered[1]=checkAirplaneModeState();
                }
                else if(i==2) {
                    isBroadCastTriggered[2]=checkHeadsetState();
                }
            }else{
                infoDesc[i] = getResources().getStringArray(R.array.settings_info_desc_disabled)[i];
            }
        }

        SettingsPreferences.setIsBroadCastTriggered(isBroadCastTriggered);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_info_recycler_view,container, false);

        mSettingsInfoRecyclerView = (RecyclerView) view.findViewById(R.id.settings_info_recycler_view);
        mSettingsInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSettingsInfoAdapter = new SettingsInfoAdapter();
        mSettingsInfoRecyclerView.setAdapter(mSettingsInfoAdapter);

        return view;
    }

    @Override
    //checks if headset or airplane mode is triggered and then updates the RecyclerView
    public void updateInfo() {
        checkOnOffStates();
        mSettingsInfoAdapter.notifyDataSetChanged();
    }

    private class SettingsInfoHolder extends RecyclerView.ViewHolder{

        private TextView mInfoText;
        private TextView mInfoDesc;
        private ImageView mInfoImage;

        public SettingsInfoHolder(View itemView) {
            super(itemView);
            mInfoText = (TextView) itemView.findViewById(R.id.settings_info_title);
            mInfoDesc = (TextView) itemView.findViewById(R.id.settings_info_description);
            mInfoImage = (ImageView) itemView.findViewById(R.id.settings_info_image_view);
        }

        private void bindSettingsInfoHolder(int imageResource, String infoTitle, String infoDesc){
            mInfoText.setText(infoTitle);
            mInfoDesc.setText(infoDesc);
            mInfoImage.setImageResource(imageResource);
        }
    }

    private class SettingsInfoAdapter extends RecyclerView.Adapter<SettingsInfoHolder>{

        @Override
        public SettingsInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_settings_info,parent, false);
            return new SettingsInfoHolder(view);
        }

        @Override
        public void onBindViewHolder(SettingsInfoHolder holder, int position) {
            //boolean array which tells if checkbox is ticked in SettingsFragment
            boolean[] isChecked = SettingsPreferences.getSettingsPreferences(getActivity());

            //boolean array which tells if airplane mode is on or headset is plugged
            boolean[] isTriggered = SettingsPreferences.getIsBroadCastTriggered();

            if(isChecked[position] && isTriggered[position]){
                holder.bindSettingsInfoHolder(imageIdOn[position],infoTitle[position],infoDesc[position]);
            }else if (!isTriggered[position] || !isChecked[position]){
                holder.bindSettingsInfoHolder(imageIdOff[position],infoTitle[position],infoDesc[position]);
            }
        }

        @Override
        public int getItemCount() {
            return SettingsPreferences.PREF_SETTINGS_LIST.length;
        }
    }
}
