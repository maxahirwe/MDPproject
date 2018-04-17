package com.hillygeeks.mdpproject;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Switch;

import static com.hillygeeks.mdpproject.ETabs.FIND_RIDE;

/**
 * Created by rMohanraj on 4/7/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ETabs tabs[]={FIND_RIDE, ETabs.POST_RIDE, ETabs.REQ_RIDE};
    Context applicationContext;


    public ViewPagerAdapter(FragmentManager manager, Context applicationContext) {
        super(manager);
        this.applicationContext=applicationContext;
    }

    @Override
    public Fragment getItem(int position) {

        ETabs tab=ETabs.getTabFromInt(position);

        switch (tab){
            case FIND_RIDE:
                return new FindRideFragment();
            case REQ_RIDE:
                return new RequestRideFragment();
            case POST_RIDE:
                return new PostRideFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position].getTabName();
    }
}