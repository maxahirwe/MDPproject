package com.hillygeeks.mdpproject;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.hillygeeks.mdpproject.DataClasses.Location;
import com.hillygeeks.mdpproject.DataClasses.Ride;
import com.hillygeeks.mdpproject.DataClasses.RideType;
import com.hillygeeks.mdpproject.DataClasses.Vehicle;
import com.hillygeeks.mdpproject.DataClasses.VehicleType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FindRideFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RidesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Ride> Ridesset =new ArrayList<>();
    List<Ride> Rideset_filtered=new ArrayList<>();
    EditText search_origin,search_destination,search_datetime;
    CheckBox filter_returning,filter_share_cost;
    Button reset_filter_btn, request_btn;



    public FindRideFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchRides();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_ride, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        search_origin=view.findViewById(R.id.txt_search_origin);
        search_destination=view.findViewById(R.id.txt_search_destination);
        search_datetime=view.findViewById(R.id.txt_search_time);
        filter_returning=view.findViewById(R.id.checkBox_search_returning);
        filter_share_cost=view.findViewById(R.id.checkBox_search_sharecost);
        reset_filter_btn =view.findViewById(R.id.reset_btn);
        request_btn =view.findViewById(R.id.request_btn);
        search_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SingleDateAndTimePickerDialog.Builder(getContext())
                        .mainColor(getResources().getColor(R.color.colorPrimary))
                        .mustBeOnFuture()
                        .displayHours(false)
                        .displayMinutes(false)
                        .title("Departure Date")
                        .listener(new SingleDateAndTimePickerDialog.Listener() {
                            @Override
                            public void onDateSelected(Date date) {
                                String choosetime=new SimpleDateFormat("MM-dd-yyyy").format(date);
                                search_datetime.setText(choosetime);
                            }
                        }).display();
            }
        });

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//                recyclerView.setLayoutManager(linearLayoutManager);
//
//                int visibleItemCount = recyclerView.getChildCount();
//                int totalItemCount = linearLayoutManager.getItemCount();
//                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
//                Log.d("recycle view","visible items"+String.valueOf(recyclerView.getChildCount()));
//                Log.d("recycle view","totalItemCount"+String.valueOf(totalItemCount));
//                Log.d("recycle view","first visible item"+String.valueOf(firstVisibleItem));
            }
        });
        mAdapter = new RidesAdapter(Ridesset);
        mRecyclerView.setAdapter(mAdapter);

        reset_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_datetime.setText("");
                search_destination.setText("");
                search_origin.setText("");
                filter_returning.setChecked(false);
                filter_share_cost.setChecked(false);
                resetRideset();
            }
        });

        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String origin=search_origin.getText().toString();
                Location location_origin=new Location();
                location_origin.setAddress(origin);
                String destination=search_destination.getText().toString();
                Location location_destination=new Location();
                location_destination.setAddress(destination);
                String datetime =search_datetime.getText().toString();
                Boolean returning=filter_returning.isChecked();
                Boolean sharecost=filter_share_cost.isChecked();
                if (!(origin.isEmpty() && destination.isEmpty() && datetime.isEmpty())){
                    //attempt tosave
                    String provider="Username";
                    Vehicle vehicle=new Vehicle();
                    vehicle.setType(VehicleType.SEDAN);
                    Ride ride=new Ride(vehicle,location_origin,location_destination, datetime,returning,sharecost,1);
                    ride.setProvider(provider);
                    ride.setType(RideType.Offer);
                }
                else{
                    Application.ShowToast(getContext(),"Please Fill All Fields To Request a Ride");
                }

            }
        });

        filter_returning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkbox_filter(b,filter_share_cost.isChecked());
            }
        });

        filter_share_cost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkbox_filter(filter_returning.isChecked(),b);
            }
        });


        TextWatcher search_queries_watcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                origin_destination_filter(search_origin.getText().toString(),search_destination.getText().toString());

            }
        };
        search_origin.addTextChangedListener(search_queries_watcher);
        search_destination.addTextChangedListener(search_queries_watcher);

        return view;
    }


    public void fetchRides(){
        ChildEventListener rides_data_listener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("new data", "onChildAdded:" + dataSnapshot.getKey());
                // A new comment has been added, ad it to the displayed list
                Ride ride = dataSnapshot.getValue(Ride.class);
                Ridesset.add(ride);
                Collections.reverse(Ridesset);
                Log.d("app",ride.getVehicle().toString());
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Ride ride = dataSnapshot.getValue(Ride.class);
                String ridetKey = dataSnapshot.getKey();
                Ridesset.add(ride);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    Application.RidesQuery.addChildEventListener(rides_data_listener);
    }


    public void dummyData(){
        Vehicle vehicle=new Vehicle(VehicleType.SEDAN,"Toyota","-");
        String datetime=new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date());
        Location location=new Location();
        location.setAddress("Fairfield");
        Location location1=new Location();
        location1.setAddress("Chicago");
        Boolean returning=true;
        Boolean sharecost=false;
        Ride ride=new Ride(vehicle,location,location1, datetime,returning,sharecost,1);
        ride.setProvider("Max");
        Ridesset.add(ride);
        Ridesset.add(ride);
        Ridesset.add(ride);
    }


    public void resetRideset(){
        Ridesset=new ArrayList<>();
        mAdapter.setRides(Ridesset);
        fetchRides();
    }

    public void origin_destination_filter(String origin,String destination){
        Rideset_filtered=new ArrayList<>();
        if(!origin.isEmpty()){
            for(Ride ride:copyRidesset()){
                if(ride.getOrigin().getAddress().toLowerCase().contains(origin)){
                    Rideset_filtered.add(ride);
                }
            }
            mAdapter.setRides(Rideset_filtered);
            mAdapter.notifyDataSetChanged();

        }
         if(!destination.isEmpty()){
            for(Ride ride:copyRidesset()){
                if(ride.getDestination().getAddress().toLowerCase().contains(destination)){
                    Rideset_filtered.add(ride);
                }
            }
            mAdapter.setRides(Rideset_filtered);
            mAdapter.notifyDataSetChanged();
        }

        if(origin.isEmpty() && destination.isEmpty()) {
            Log.d("status","resetting dataset");
            resetRideset();
        }


    }



    public void checkbox_filter(final Boolean returning, Boolean sharecost){
        Log.d("status_filter","returning:"+returning);
        Log.d("status_filter","sharecost:"+sharecost);
        if (returning || sharecost) {
            Rideset_filtered=new ArrayList<>();
            for(Ride ride:copyRidesset()){
                if(ride.getReturning()==returning && ride.getShareCost()==sharecost){
                    Rideset_filtered.add(ride);
                }
            }
            mAdapter.setRides(Rideset_filtered);
            mAdapter.notifyDataSetChanged();
        }
        else {
            Log.d("status","resetting dataset");
            resetRideset();
        }
    }

    public List<Ride> copyRidesset(){
        List<Ride> Rideset_copy=new ArrayList<>();
        Rideset_copy.addAll(Ridesset);
        return Rideset_copy;
    }
}
