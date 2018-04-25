package com.hillygeeks.mdpproject.MessagingService;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.hillygeeks.mdpproject.DataClasses.Ride;
import com.hillygeeks.mdpproject.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NotificationSenderAPI extends AsyncTask<String, Void, String> {

    Context context;
    Ride rideInfo;
    final String notificationSender="Notification Sender: ";
    NotificationSenderAPI(Context context, Ride rideInfo){
        this.context=context;
        this.rideInfo=rideInfo;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            URL url = new URL(context.getResources().getString(R.string.notificationAPI));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", context.getResources().getString(R.string.serverKey));
            DataOutputStream streamWriter = new DataOutputStream(connection.getOutputStream());

            String request = new Gson().toJson(rideInfo);
            Log.d("Notification Request: ", request);
            streamWriter.writeBytes(request);
            connection.connect();


            if (connection.getResponseCode() == 200) {
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line + "\n";
                }
            }

            streamWriter.flush();
            streamWriter.close();

            Log.d("Notification Response: ", response);

        } catch (Exception e) {
            Log.d("Notification Except: ", e.getMessage());

        }

        return response;
    }
}
