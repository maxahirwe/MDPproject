package com.hillygeeks.mdpproject.MessagingService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hillygeeks.mdpproject.R;
import com.hillygeeks.mdpproject.RidesActivity;

import org.json.JSONObject;

import java.util.Map;

public class NotificationReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("Notification From: ", remoteMessage.toString());

        //Create a new intent
        Intent intent=new Intent(this, RidesActivity.class);

        //If set, and the activity being launched is already running in the current task,
        // then instead of launching a new instance of that activity, all of the other
        // activities on top of it will be closed and this Intent will be delivered to
        // the (now on top) old activity as a new Intent.
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        //In case if the application is receiving multiple notification, we have to generate
        // unique id each time, otherwise we will loss all the unreaded old notifications
        // when the new notification comes in
        int notificationId = (int) System.currentTimeMillis();

        //Create a pending indent FLAG_ONE_SHOT: Flag indicating that this PendingIntent can be used only once
        PendingIntent pendingIntent=PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);

        //Get the information related to ride
        Map<String, String> rideInfo = remoteMessage.getData();
        JSONObject rideInfoJson = new JSONObject(rideInfo);
        Log.e("Post Request: ", rideInfoJson.toString());

        //Build push notification
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("Ride Share");
        notificationBuilder.setContentText(rideInfoJson.toString());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);

        //Finally display notification
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
