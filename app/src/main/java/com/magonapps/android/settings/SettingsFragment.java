package com.magonapps.android.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by Gonzales on 9/21/2017.
 */

public class SettingsFragment extends Fragment {

    private RecyclerView mSettingsRecyclerView;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_recycler_view,container,false);
        mSettingsRecyclerView = (RecyclerView) view.findViewById(R.id.settings_recycler_view);
        mSettingsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSettingsRecyclerView.setAdapter(new SettingsAdapter());

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsPreferences.setSettingsPreferences(getActivity());
    }

    private class SettingsHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDescription;
        private CheckBox mCheckBox;

        public SettingsHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.settings_title);
            mDescription = (TextView) itemView.findViewById(R.id.settings_description);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.settings_check_box);
        }

        public void onBindSettingsViewHolder(int titleId, int descId, boolean isOn, final int position){
            mTitle.setText(titleId);
            mDescription.setText(descId);
            mCheckBox.setChecked(isOn);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    SettingsPreferences.setSettingsList(
                            SettingsPreferences.PREF_SETTINGS_LIST[position],
                            getActivity(),
                            b
                    );
                    if(position==0 && !b)
                        SettingsPreferences.setPowerCount(0);
                }
            });
        }
    }

    private class SettingsAdapter extends RecyclerView.Adapter<SettingsHolder>{

        private final int[] titleReferences = {
                R.string.power_title,
                R.string.airplane_mode_on_title,
                R.string.headset_plugged_title
        };

        private final int[] descReferences = {
                R.string.power_description,
                R.string.airplane_mode_on_description,
                R.string.headset_plugged_description
        };

        @Override
        public SettingsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_setting, parent, false);
            return new SettingsHolder(view);
        }

        @Override
        public void onBindViewHolder(SettingsHolder holder, int position) {

            holder.onBindSettingsViewHolder(
                    titleReferences[position],
                    descReferences[position],
                    SettingsPreferences.getSettingsPreferences(getActivity())[position],
                    position
            );

        }

        @Override
        public int getItemCount() {
            return SettingsPreferences.PREF_SETTINGS_LIST.length;
        }
    }




}
