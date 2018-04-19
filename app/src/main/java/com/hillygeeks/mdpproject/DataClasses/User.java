package com.hillygeeks.mdpproject.DataClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    String Fname,Lname,Email,Gender;

    protected User(Parcel in) {
        Fname = in.readString();
        Lname = in.readString();
        Email = in.readString();
        Gender = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Fname);
        parcel.writeString(Lname);
        parcel.writeString(Email);
        parcel.writeString(Gender);
    }
}
