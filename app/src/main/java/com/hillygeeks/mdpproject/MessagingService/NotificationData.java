package com.hillygeeks.mdpproject.MessagingService;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationData implements Parcelable {
    String From;
    String To;
    String message;
    String rideType;

    NotificationData(String from, String To, String message, String rideType){
        this.From =from;
        this.To=To;
        this.message=message;
        this.rideType=rideType;
    }

    protected NotificationData(Parcel in) {
        From = in.readString();
        To=in.readString();
        message = in.readString();
        rideType = in.readString();
    }

    public static final Creator<NotificationData> CREATOR = new Creator<NotificationData>() {
        @Override
        public NotificationData createFromParcel(Parcel in) {
            return new NotificationData(in);
        }

        @Override
        public NotificationData[] newArray(int size) {
            return new NotificationData[size];
        }
    };



    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        this.From = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(From);
        parcel.writeString(To);
        parcel.writeString(message);
        parcel.writeString(rideType);

    }
}