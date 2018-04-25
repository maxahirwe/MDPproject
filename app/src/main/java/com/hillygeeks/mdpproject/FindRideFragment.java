package com.hillygeeks.mdpproject;


import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    List<Ride> Ridesset;
    List<Ride> Rideset_filtered=new ArrayList<>();
    EditText search_origin,search_destination,search_datetime;
    CheckBox filter_returning,filter_share_cost;
    Button reset_filter_btn, request_btn;
    RideType type;


    public FindRideFragment() {
        // Required empty public constructor
    }

    public static FindRideFragment newInstance (RideType type) {
        FindRideFragment fragment = new FindRideFragment();
        fragment.type=type;
        return fragment;
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
        if(type==RideType.Request || type==RideType.Booking){
            request_btn.setVisibility(View.GONE);
        }
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
                if (!origin.isEmpty() && !destination.isEmpty() && !datetime.isEmpty()){
                    Vehicle vehicle=new Vehicle();
                    vehicle.setType(VehicleType.SEDAN);
                    Ride ride=new Ride(vehicle,location_origin,location_destination, datetime,null,returning,sharecost,1);
                    ride.setCreator(Application.username);
                    ride.setClient(Application.username);
                    ride.setType(RideType.Request);
                    String key = Application.RidesRef.push().getKey();
                    Application.RidesRef.child(key).setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("db status","Ride Saved");
                            Application.ShowToast(getContext(),"Ride Request Created");
                            search_destination.setText("");
                            search_origin.setText("");
                            search_datetime.setText("");
                        }
                    });

                }
                else{
                    Application.ShowToast(getContext(),"Please Fill All Fields To Request a Ride");
                }

            }
        });

        filter_returning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Rideset_filtered=checkbox_filter(b,filter_share_cost.isChecked());
                mAdapter.setRides(Rideset_filtered);
                mAdapter.notifyDataSetChanged();


            }
        });

        filter_share_cost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Rideset_filtered=checkbox_filter(filter_returning.isChecked(),b);
                mAdapter.setRides(Rideset_filtered);
                mAdapter.notifyDataSetChanged();
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
                String origin=search_origin.getText().toString();
                String destination=search_destination.getText().toString();
                String datetime=search_datetime.getText().toString();
                Boolean returning=filter_returning.isChecked();
                Boolean share_cost=filter_share_cost.isChecked();
                if(origin.isEmpty() && destination.isEmpty() && datetime.isEmpty()) {
                    Log.d("status","resetting dataset");
                    resetRideset();
                }else{
                Rideset_filtered= filter_rides(origin,destination,datetime,returning,share_cost);
                mAdapter.setRides(Rideset_filtered);
                mAdapter.notifyDataSetChanged();
                }
            }
        };
        search_origin.addTextChangedListener(search_queries_watcher);
        search_destination.addTextChangedListener(search_queries_watcher);
        search_datetime.addTextChangedListener(search_queries_watcher);

        return view;
    }


    public void fetchRides(){
        Ridesset=new ArrayList<>();
        ChildEventListener rides_data_listener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // A new comment has been added, ad it to the displayed list
                Ride ride = dataSnapshot.getValue(Ride.class);
                ride.id=dataSnapshot.getKey();
                if (!Ridesset.contains(ride) && ride.getType()==type){
                    Log.d("new data", "added onChildAdded:" + dataSnapshot.getKey());
                    Ridesset.add(ride);
                    Collections.sort(Ridesset);
                    mAdapter.setRides(Ridesset);
                    mAdapter.notifyDataSetChanged();
                }else{
                    Log.d("duplicate data", "already in list ride:" + dataSnapshot.getKey());
                    mAdapter.setRides(Ridesset);
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // A comment has changed, use the key to determine if we are displaying this
                Ride ride = dataSnapshot.getValue(Ride.class);
                ride.id=dataSnapshot.getKey();
                if (!Ridesset.contains(ride) && ride.getType()==type && !ride.getBooked()){
                    Log.d("new data", "added onChildAdded:" + dataSnapshot.getKey());
                    Ridesset.add(ride);
                    Collections.sort(Ridesset);
                    mAdapter.setRides(Ridesset);
                    mAdapter.notifyDataSetChanged();
                }else{
                    Log.d("duplicate data", "already in list ride:" + dataSnapshot.getKey());
                    mAdapter.setRides(Ridesset);
                    mAdapter.notifyDataSetChanged();

                }
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


    public void resetRideset(){
        Ridesset=new ArrayList<>();
        Rideset_filtered=new ArrayList<>();
        mAdapter.setRides(Ridesset);
        mAdapter.notifyDataSetChanged();
        fetchRides();
    }

    public List<Ride> filter_rides(String origin, String destination, String date, boolean returning, boolean sharecost ){
        List<Ride> Rideset_filtered=new ArrayList<>();
        List<Ride> final_set=new ArrayList<>();
        //when all fields are filled
        if(!origin.isEmpty() && !destination.isEmpty() && !date.isEmpty()){
            Log.d("status"," filter,all fields are filled");
            for(Ride ride:copyRidesset()){
                if(ride.getOrigin().getAddress().toLowerCase().contains(origin)
                        && ride.getDestination().getAddress().toLowerCase().contains(destination)
                        && ride.getDepart_datetime().toLowerCase().contains(date)){
                    Rideset_filtered.add(ride);
                }
            }

        }

        //when origin and destination are the only filled field
        else if(!origin.isEmpty() && !destination.isEmpty() && date.isEmpty()){
            Log.d("status"," filter,destination and origin are filled");
            for(Ride ride:copyRidesset()){
                if(ride.getOrigin().getAddress().toLowerCase().contains(origin)
                        && ride.getDestination().getAddress().toLowerCase().contains(destination) ){
                    Rideset_filtered.add(ride);
                }
            }

        }

        //when only origin is the only filled field
        else if(!origin.isEmpty() && destination.isEmpty() && date.isEmpty()){
            Log.d("status"," filter,origin is filled");
            for(Ride ride:copyRidesset()){
                if(ride.getOrigin().getAddress().toLowerCase().contains(origin)){
                    Rideset_filtered.add(ride);
                }
            }
        }


        //when destination is the only filled field
        else if(origin.isEmpty() && !destination.isEmpty() && date.isEmpty()){
            Log.d("status"," filter,destination is filled");
            for(Ride ride:copyRidesset()){
                if(ride.getDestination().getAddress().toLowerCase().contains(destination)){
                    Rideset_filtered.add(ride);
                }
            }
        }

        //when date is the only filled field
        else if(origin.isEmpty() && destination.isEmpty() && !date.isEmpty()){
            Log.d("status"," filter,date is filled");
            for(Ride ride:copyRidesset()){
                if(ride.getDepart_datetime().toLowerCase().contains(date)){
                    Rideset_filtered.add(ride);
                }
            }
        }

        Log.d("filtering","filter size:"+Rideset_filtered.size());
        List<Ride> to_checkbox_filter=null;
        if (origin.isEmpty() && destination.isEmpty() && date.isEmpty()){
            to_checkbox_filter=new ArrayList<>(Ridesset);
            Log.d("filtering","no other filter size:"+Rideset_filtered.size());
        }else{
            to_checkbox_filter=new ArrayList<>(Rideset_filtered);
        }

        //filter based on checkboxes
            for(Ride ride:to_checkbox_filter){
                if(ride.getReturning()==returning && ride.getShareCost()==sharecost){
                    Log.d("filtering","added:"+ride.toString());
                    final_set.add(ride);
                }
            }


        return final_set;
    }



    public List<Ride> checkbox_filter(final Boolean returning, final Boolean sharecost){
        Log.d("status_filter","returning:"+returning);
        Log.d("status_filter","sharecost:"+sharecost);
        String origin=search_origin.getText().toString();
        String destination=search_destination.getText().toString();
        String datetime=search_datetime.getText().toString();
        return filter_rides(origin,destination,datetime,returning,sharecost);
    }

    public List<Ride> copyRidesset(){
        List<Ride> Rideset_copy=new ArrayList<>();
        Rideset_copy.addAll(Ridesset);
        return Rideset_copy;
    }
}
