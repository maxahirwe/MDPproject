package com.hillygeeks.mdpproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hillygeeks.mdpproject.DataClasses.User;

public class Application extends android.app.Application {
    static FirebaseDatabase database ;
    static DatabaseReference RidesRef;
    static DatabaseReference UsersRef;
    static final int RIDES_LOAD=50;
    static Query RidesQuery;
    static Query OfferedRidesQuery;
    static Query RequestedidesQuery;
    static User user;
    static String PREF="ShareRide";
    static SharedPreferences sharedpreferences;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        user=new User();
        sharedpreferences=getSharedPreferences(PREF, Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        RidesRef = database.getReference("rides");
        UsersRef = database.getReference("users");
        RidesQuery=RidesRef.limitToLast(RIDES_LOAD);
        OfferedRidesQuery=RidesRef.orderByChild("provider").equalTo(user.userid);
        RequestedidesQuery=RidesRef.orderByChild("client").equalTo(user.userid);
    }


    public static void ShowToast(Context ctx, String msg){
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }


}







