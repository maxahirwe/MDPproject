package com.hillygeeks.mdpproject.DataClasses;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Location {
    String City;
    String Address;
    public Location(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Location(String Address){
       this.Address=Address;
    }

    public Location(String city, String address) {
        City = city;
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
