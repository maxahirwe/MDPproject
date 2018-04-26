package com.hillygeeks.mdpproject.DataClasses;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Vehicle {
    VehicleType type;
    String maker, color;
    public Vehicle(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Vehicle(VehicleType type, String maker, String color) {
        this.type = type;
        this.maker = maker;
        this.color = color;
    }

    public Vehicle(String maker, String color) {
        this.type =VehicleType.SEDAN;
        this.maker = maker;
        this.color = color;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
