package com.magonapps.android.settings;

import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Gonzales on 9/22/2017.
 */

public class SettingsPagerActivity extends AppCompatActivity{
    private ViewPager mViewPager;
    private PagerCallback mPagerCallback;

    public interface PagerCallback{
        void updateInfo();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view_pager);

        Log.d("SettingsInfoFragment","created");


        mViewPager = (ViewPager) findViewById(R.id.settings_view_pager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {

            @Override
            public Fragment getItem(int position) {
                switch(position){
                    case 0:
                        return SettingsFragment.newInstance();
                    case 1:
                        Fragment settingsInfo = SettingsInfoFragment.newInstance();
                        mPagerCallback = (PagerCallback) settingsInfo;
                        return settingsInfo;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 1:
                        if(SettingsPagerActivity.this.mPagerCallback!=null)
                            SettingsPagerActivity.this.mPagerCallback.updateInfo();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        };

}
