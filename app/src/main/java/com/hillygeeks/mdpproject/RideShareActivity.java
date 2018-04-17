package com.hillygeeks.mdpproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RideShareActivity extends AppCompatActivity {

    // Need to use the requirements of the objects for FragmentManager and FragmentTransaction;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_share);

        // assign and get the object for the FragmentManager by using the below statements
        fragmentManager = getFragmentManager();

        //get the object for FragmentTransaction and Initialize the transaction
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame1,new FindRideFragment());

        // Commit the fragment transaction
        fragmentTransaction.commit();
    }

    public void onFindRide(View view) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1,new FindRideFragment());
        fragmentTransaction.commit();
    }

    public void onRequestRide(View view) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1,new RequestRideFragment());
        fragmentTransaction.commit();
    }

    public void onPostRide(View view) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame1,new PostRideFragment());
        fragmentTransaction.commit();
    }
}
