package com.hillygeeks.mdpproject;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Application extends android.app.Application {
    static FirebaseDatabase database ;
    static DatabaseReference RidesRef;
    protected GeoDataClient mGeoDataClient;

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        database = FirebaseDatabase.getInstance();
        RidesRef = database.getReference("rides");


    }


    public static void ShowToast(Context ctx, String msg){
        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
    }


}







