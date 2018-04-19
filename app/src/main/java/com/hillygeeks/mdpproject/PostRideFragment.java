package com.hillygeeks.mdpproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hillygeeks.mdpproject.DataClasses.PostRide;
import com.hillygeeks.mdpproject.DataClasses.User;
import com.hillygeeks.mdpproject.rulesets.RuleException;
import com.hillygeeks.mdpproject.rulesets.RuleSet;
import com.hillygeeks.mdpproject.rulesets.RuleSetFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class PostRideFragment extends Fragment {

    Calendar datetime = Calendar.getInstance();

    //Controls
    EditText etDepDate;
    EditText etDepTime;
    EditText etFromLocation;
    EditText etToLocation;
    EditText etPrice;
    EditText etTotalSeats;
    EditText etDescription;

    Button btnPost;

    //Dialogs
    DatePickerDialog dlgDatePicker;
    TimePickerDialog dlgTimePicker;


    User user;

    DatabaseReference databasePostRides;

    // DatePickerDialog Listener Implementation to set the Picked Date
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            datetime.set(Calendar.YEAR,year);
            datetime.set(Calendar.MONTH,month);
            datetime.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateDate();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            datetime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            datetime.set(Calendar.MINUTE,minute);
            updateTime();
        }};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_ride, container, false);

        //Current user
        user=getArguments().getParcelable(getResources().getString(R.string.keyCurrentUser));

        //Init controllers
        initControls(view);

        //Init database
        databasePostRides=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.tblPostRides));

        return view;
    }


    private void initControls(View view){
        etDepDate=view.findViewById(R.id.etDepDate);
        etDepDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                dlgDatePicker =new DatePickerDialog(getActivity(),d,datetime.get(Calendar.YEAR),
                        datetime.get(Calendar.MONTH),
                        datetime.get(Calendar.DAY_OF_MONTH));
                dlgDatePicker.show();
            }
        });

        etDepTime=view.findViewById(R.id.etDepTime);
        etDepTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                dlgTimePicker =new TimePickerDialog(getActivity(),t,datetime.get(Calendar.HOUR_OF_DAY),
                        datetime.get(Calendar.MINUTE),true);
                dlgTimePicker.show();
            }
        });

        etFromLocation=view.findViewById(R.id.etFromLocation);
        etToLocation=view.findViewById(R.id.etToLocation);
        etPrice=view.findViewById(R.id.etPrice);
        etTotalSeats=view.findViewById(R.id.etTotalSeats);
        etDescription=view.findViewById(R.id.etDescription);
        btnPost=view.findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRide();
            }
        });
    }

    private void postRide(){

        PostRide postRide=null;
        try {
            RuleSet rules = RuleSetFactory.getRuleSet(this);
            postRide=(PostRide) rules.applyRules(this);
        }
        catch (RuleException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        String idPostRide=databasePostRides.push().getKey();

        databasePostRides.child(idPostRide).setValue(postRide);
        Toast.makeText(getContext(), "Ride posted successfully", Toast.LENGTH_LONG).show();
    }




    private void updateDate() {
        DateFormat dateFormat = new SimpleDateFormat(getContext().getString(R.string.fmtDate));
        etDepDate.setText(dateFormat.format(datetime.getTime()));
        //dlgDatePicker.cancel();
    }

    private void updateTime() {
        DateFormat timeFormat = new SimpleDateFormat(getResources().getString(R.string.fmtTime));
        etDepTime.setText(timeFormat.format(datetime.getTime()));
        //dlgTimePicker.cancel();
    }


    public String getDepDateTime(){
        return  etDepDate.getText()+" "+etDepTime.getText();
    };
    public String getFromLocation(){
        return ""+etFromLocation.getText();
    };
    public String getToLocation(){
        return ""+etToLocation.getText();
    };
    public String getPrice(){
        return ""+etPrice.getText();
    };
    public String getTotalSeats(){
        return ""+etTotalSeats.getText();
    };
    public String getDescription() {
        return ""+etDescription.getText();
    }
}
