package com.hillygeeks.mdpproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hillygeeks.mdpproject.DataClasses.RideType;
import com.hillygeeks.mdpproject.MessagingService.NotificationPayload;
import com.hillygeeks.mdpproject.MessagingService.NotificationSenderAPI;

import java.util.ArrayList;
import java.util.List;

public class RidesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    NotificationPayload notificationPayload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        String name=Application.sharedpreferences.getString("user_name","-"+"");
        toolbar.setSubtitle(Application.user.getEmail()+name);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        if (intent == null)
            return;

        notificationPayload = intent.getParcelableExtra(getResources().getString(R.string.keyOpenAlertDlg));
        if (notificationPayload == null)
            return;

        displayRideConfirmationDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_main_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FindRideFragment.newInstance(RideType.Offer), "Find");
        adapter.addFragment(FindRideFragment.newInstance(RideType.Request), "Offer");
        adapter.addFragment(new RegisterRideFragment(), "Share");
        adapter.addFragment(FindRideFragment.newInstance(RideType.Booking), "Bookings");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void displayRideConfirmationDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("Ride Request!");
        String message=notificationPayload.getData().getMessage()+", please confirm";
        builder.setMessage(message);
        builder.setIcon(R.drawable.ride);
        AlertDialog.OnClickListener listener=new AlertDialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String message="";
                if(which==dialog.BUTTON_POSITIVE)
                    message=getResources().getString(R.string.notificationMessage2);
                else if(which==dialog.BUTTON_NEGATIVE)
                    message=getResources().getString(R.string.notificationMessage3);

                String to=notificationPayload.getData().getFrom();
                String from= FirebaseInstanceId.getInstance().getToken();
                String rideType=getResources().getString(R.string.rideConformer);

                //<<TODO: Update database, and mark ride as booked
                Application.SendNotificaion(from, to, rideType, message, getApplicationContext());
                finish();
            }
        };
        builder.setPositiveButton("Yes", listener);
        builder.setNegativeButton("No", listener);
        builder.show();
    }
}
