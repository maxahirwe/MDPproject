package com.hillygeeks.mdpproject;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Application extends android.app.Application {
    static FirebaseDatabase database ;
    static DatabaseReference RidesRef;
    static final int RIDES_LOAD=50;
    static Query RidesQuery;
    static Query OfferedRidesQuery;
    static Query RequestedidesQuery;
    //TODO: anything that uniquely identifies a user
    static String username="user_id";

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        RidesRef = database.getReference("rides");
        RidesQuery=RidesRef.limitToLast(RIDES_LOAD);
        OfferedRidesQuery=RidesRef.orderByChild("provider").equalTo(username);
        RequestedidesQuery=RidesRef.orderByChild("client").equalTo(username);
    }


    public static void ShowToast(Context ctx, String msg){
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }


}







