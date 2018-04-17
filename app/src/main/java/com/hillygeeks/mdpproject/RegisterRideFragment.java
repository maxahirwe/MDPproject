package com.hillygeeks.mdpproject;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hillygeeks.mdpproject.DataClasses.Location;
import com.hillygeeks.mdpproject.DataClasses.Ride;
import com.hillygeeks.mdpproject.DataClasses.Vehicle;
import com.hillygeeks.mdpproject.DataClasses.VehicleType;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Handles the registration of rides
 */
public class RegisterRideFragment extends Fragment {


    public RegisterRideFragment() {
        // Required empty public constructor
    }

    Button share_btn;

    EditText vehicle_txt,origin_txt,destination_txt,capacity_txt;
    AutoCompleteTextView vehicle_type_txt, vehicle_maker_txt;
    CheckBox checkBox_returning,checkBox_sharecost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        VehicleType vehicle_types[] = {VehicleType.SEDAN,VehicleType.MINIVAN,VehicleType.SUV,VehicleType.PICKUP};
        String[] car_makers = getResources().getStringArray(R.array.car_makers);
        ArrayAdapter<VehicleType> vehicle_type_adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,vehicle_types);
        ArrayAdapter<String> vehicle_maker_adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,car_makers);
        View view = inflater.inflate(R.layout.fragment_register_ride, container, false);
        vehicle_type_txt =view.findViewById(R.id.txt_vehicle_type);
        vehicle_maker_txt =view.findViewById(R.id.txt_vehicle_maker);
        vehicle_type_txt.setAdapter(vehicle_type_adapter);
        vehicle_maker_txt.setAdapter(vehicle_maker_adapter);
        vehicle_txt=view.findViewById(R.id.txt_vehicle_type);
        vehicle_type_txt.setThreshold(0);
        vehicle_maker_txt.setThreshold(0);
        vehicle_type_txt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                    vehicle_type_txt.showDropDown();
                return false;
            }
        });
        vehicle_maker_txt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                vehicle_maker_txt.showDropDown();
                return false;
            }
        });

        origin_txt=view.findViewById(R.id.txt_origin);
        destination_txt=view.findViewById(R.id.txt_destination);
        capacity_txt=view.findViewById(R.id.txt_capacity);
        checkBox_returning=view.findViewById(R.id.checkBox_returning);
        checkBox_sharecost=view.findViewById(R.id.checkBox_sharecost);
        share_btn=view.findViewById(R.id.share_btn);

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v_type,v_maker,origin,destination,capacity;
                v_type=vehicle_type_txt.getText().toString();
                v_maker=vehicle_maker_txt.getText().toString();
                origin=origin_txt.getText().toString();
                destination=destination_txt.getText().toString();
                capacity=capacity_txt.getText().toString();
                if(!v_type.isEmpty() && !v_maker.isEmpty() && !origin.isEmpty() && !destination.isEmpty() & !capacity.isEmpty()){
                    Vehicle vehicle=new Vehicle(VehicleType.SEDAN,v_maker,"-");
                    Location origin_location=new Location(origin);
                    Location destination_location=new Location(destination);
                    String datetime=new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date());
                    Boolean returning=checkBox_returning.isChecked();
                    Boolean sharecost=checkBox_sharecost.isChecked();
                    Ride ride=new Ride(vehicle,origin_location,destination_location, datetime,returning,sharecost,Integer.valueOf(capacity));
                    String key = Application.RidesRef.push().getKey();
                    Application.RidesRef.child(key).setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("db status","data saved bruv");
                        }
                    });

                }
                else{
                    Application.ShowToast(getContext(),"Please Fill All Fields");

                }

            }
        });

//        mDatabase.child("users").child(userId).setValue(user);
        return view;
    }


}
