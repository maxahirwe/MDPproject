package com.hillygeeks.mdpproject.DataClasses;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Location {
    String city;
    String address;
    public Location(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Location(String Address){
       this.address =Address;
    }

    public Location(String city, String address) {
        this.city = city;
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
