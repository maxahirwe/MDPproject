package com.hillygeeks.mdpproject.MessagingService;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.hillygeeks.mdpproject.R;
import com.hillygeeks.mdpproject.RidesActivity;

import org.json.JSONObject;

import java.util.Map;

public class NotificationReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("Notification From: ", remoteMessage.toString());

        //Get the information related to ride
        Map<String, String> rideInfo = remoteMessage.getData();
        JSONObject rideInfoJson = new JSONObject(rideInfo);
        Log.d("Post Request: ", rideInfoJson.toString());

        //Parse json in actual object
        NotificationPayload notificationPayload= new NotificationPayload(
                rideInfo.get("From"),
                rideInfo.get("To"),
                rideInfo.get("rideType"),
                rideInfo.get("message")
        );//new Gson().fromJson(rideInfoJson.toString(), NotificationPayload.class);

        //Create a new intent
        Intent intent=new Intent(this, RidesActivity.class);
        Resources res=getResources();
        if(notificationPayload.getData().getRideType().equals(res.getString(R.string.rideRequester))) {
            intent.putExtra(res.getString(R.string.keyOpenAlertDlg), notificationPayload);
        }



        //If set, and the activity being launched is already running in the current task,
        // then instead of launching a new instance of that activity, all of the other
        // activities on top of it will be closed and this Intent will be delivered to
        // the (now on top) old activity as a new Intent.
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        //In case if the application is receiving multiple notification, we have to generate
        // unique id each time, otherwise we will loss all the unreaded old notifications
        // when the new notification comes in
        int notificationId = 0;//(int) System.currentTimeMillis();

        //Create a pending indent FLAG_ONE_SHOT: Flag indicating that this PendingIntent can be used only once
        PendingIntent pendingIntent=PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_ONE_SHOT);

        String notificationMessage=notificationPayload.getData().getMessage();
        //Build push notification
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("Ride Share");
        notificationBuilder.setContentText(notificationMessage);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.ride);
        notificationBuilder.setChannelId("my_channel_01");
        notificationBuilder.setContentIntent(pendingIntent);

        //Finally display notification
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = "Ride App";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
