package com.hillygeeks.mdpproject.DataClasses;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Vehicle {
    VehicleType Type;
    String Maker,Color;

    public Vehicle(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Vehicle(VehicleType type, String maker, String color) {
        Type = type;
        Maker = maker;
        Color = color;
    }

    public Vehicle(String maker, String color) {
        this.Type=VehicleType.SEDAN;
        Maker = maker;
        Color = color;
    }

    public VehicleType getType() {
        return Type;
    }

    public void setType(VehicleType type) {
        Type = type;
    }

    public String getMaker() {
        return Maker;
    }

    public void setMaker(String maker) {
        Maker = maker;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
