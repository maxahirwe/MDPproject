package com.hillygeeks.mdpproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Switch;

import com.hillygeeks.mdpproject.DataClasses.User;

import static com.hillygeeks.mdpproject.ETabs.FIND_RIDE;

/**
 * Created by rMohanraj on 4/7/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ETabs tabs[]={FIND_RIDE, ETabs.POST_RIDE, ETabs.REQ_RIDE};
    Context context;
    User user;

    public ViewPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        this.context=context;
        this.user=user;
    }

    @Override
    public Fragment getItem(int position) {

        ETabs tab=ETabs.getTabFromInt(position);

        Fragment fragment=null;

        switch (tab){
            case FIND_RIDE:
                 new FindRideFragment();
            case REQ_RIDE:
                fragment = new RequestRideFragment();
            case POST_RIDE:
                fragment = new PostRideFragment();
        }

        //Pass current user to fragment
        Bundle bundle = new Bundle();
        String keyCurrentUser=context.getResources().getString(R.string.keyCurrentUser);
        bundle.putParcelable(keyCurrentUser, user);
        fragment.setArguments(bundle);
        return fragment;
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