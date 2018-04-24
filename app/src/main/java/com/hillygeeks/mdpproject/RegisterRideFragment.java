package com.hillygeeks.mdpproject;


import android.content.Context;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.DoubleDateAndTimePickerDialog;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.hillygeeks.mdpproject.DataClasses.Location;
import com.hillygeeks.mdpproject.DataClasses.Ride;
import com.hillygeeks.mdpproject.DataClasses.RideType;
import com.hillygeeks.mdpproject.DataClasses.Vehicle;
import com.hillygeeks.mdpproject.DataClasses.VehicleType;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Handles the registration of rides
 */
public class RegisterRideFragment extends Fragment {


    public RegisterRideFragment() {
        // Required empty public constructor
    }
    int capacity=1;
    Location location_origin,location_destination;
    Button share_btn,capacity_plus_btn,capacity_minus_btn;
    PlacesAutocompleteTextView origin_txt,destination_txt;
    EditText vehicle_txt,capacity_txt, datetime_departure_txt,datetime_returning_txt;
    AutoCompleteTextView vehicle_type_txt, vehicle_maker_txt;
    CheckBox checkBox_returning,checkBox_sharecost;
    LinearLayout returning_time_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        location_origin=new Location();
        location_destination=new Location();
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

        origin_txt.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        location_origin.setAddress(place.description);
                    }
                }
        );

        destination_txt=view.findViewById(R.id.txt_destination);
        destination_txt.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        location_destination.setAddress(place.description);
                    }
                }
        );
        capacity_txt=view.findViewById(R.id.txt_capacity);
        checkBox_returning=view.findViewById(R.id.checkBox_returning);
        checkBox_sharecost=view.findViewById(R.id.checkBox_sharecost);
        share_btn=view.findViewById(R.id.share_btn);
        capacity_minus_btn=view.findViewById(R.id.btn_capacity_minus);
        capacity_plus_btn=view.findViewById(R.id.btn_capacity_plus);
        setupCapacityBtns();
        returning_time_layout=view.findViewById(R.id.returning_time);
        datetime_departure_txt =view.findViewById(R.id.datetime_departure_txt);
        datetime_returning_txt =view.findViewById(R.id.datetime_returning_txt);
        checkBox_returning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox_returning.isChecked()){
                    returning_time_layout.setVisibility(View.VISIBLE);
                }
                else{
                    returning_time_layout.setVisibility(View.GONE);
                }
            }
        });

        View.OnClickListener date_time_click_listener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.datetime_departure_txt:
                        DateTimePicker(view.getContext(),false);
                        break;

                    case R.id.datetime_returning_txt:
                        DateTimePicker(view.getContext(),true);
                        break;
                }
            }
        };
        datetime_departure_txt.setOnClickListener(date_time_click_listener);
        datetime_returning_txt.setOnClickListener(date_time_click_listener);
        datetime_departure_txt.setText(new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date()));
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
                    String datetime=new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date());
                    Boolean returning=checkBox_returning.isChecked();
                    Boolean sharecost=checkBox_sharecost.isChecked();
                    String provider="Username";
                    Ride ride=new Ride(vehicle,location_origin,location_destination, datetime,returning,sharecost,Integer.valueOf(capacity));
                    ride.setProvider(provider);
                    ride.setType(RideType.Offer);
                    String key = Application.RidesRef.push().getKey();
                    Application.RidesRef.child(key).setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("db status","data saved bruv");
                            Application.ShowToast(getContext(),"Ride Created");
                        }
                    });
                }
                else{
                    Application.ShowToast(getContext(),"Please Fill All Fields");
                }
            }
        });


        return view;
    }

    public void setupCapacityBtns(){
        View.OnClickListener clicklistener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capacityChange(view);
            }
        };
        capacity_plus_btn.setOnClickListener(clicklistener);
        capacity_minus_btn.setOnClickListener(clicklistener);
    }

    public void capacityChange(View view){
        switch (view.getId()){
            case R.id.btn_capacity_plus:
                if(capacity<5) capacity=capacity+1;
                break;
            case R.id.btn_capacity_minus:
               if(capacity>1) capacity=capacity-1;
                break;

        }
        capacity_txt.setText(String.valueOf(capacity));


    }

    public void DateTimePicker(Context ctx, final Boolean double_type) {

        new SingleDateAndTimePickerDialog.Builder(ctx)
                //.bottomSheet()
                //.curved()
                //.minutesStep(15)
                //.displayHours(false)
                //.displayMinutes(false)
                //.todayText("aujourd'hui")
                .mainColor(getResources().getColor(R.color.colorPrimary))
                .mustBeOnFuture()
                .title(double_type ? "Departure":"Returning")
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        String choosetime=new SimpleDateFormat("MM-dd-yyyy HH:mm").format(date);
                        if(double_type){
                            datetime_returning_txt.setText(choosetime);
                        }else{
                            datetime_departure_txt.setText(choosetime);
                        }
                    }
                }).display();

    }

}
