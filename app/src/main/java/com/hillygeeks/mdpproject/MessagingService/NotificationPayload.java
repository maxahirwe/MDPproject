package com.hillygeeks.mdpproject.MessagingService;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

public class NotificationPayload implements Parcelable {


    String to;
    NotificationData data;

    public NotificationPayload( String from, String to, String rideType, String message) {
        this.to = to;
        data =new NotificationData(from, to, rideType, message);
    }

    protected NotificationPayload(Parcel in) {
        to = in.readString();
        data = in.readParcelable(NotificationData.class.getClassLoader());
    }

    @Exclude
    public static final Creator<NotificationPayload> CREATOR = new Creator<NotificationPayload>() {
        @Override
        public NotificationPayload createFromParcel(Parcel in) {
            return new NotificationPayload(in);
        }

        @Override
        public NotificationPayload[] newArray(int size) {
            return new NotificationPayload[size];
        }
    };

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    @Exclude
    @Override
    public int describeContents() {
        return 0;
    }

    @Exclude
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(to);
        parcel.writeParcelable(data, i);
    }
}
