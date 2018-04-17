package com.hillygeeks.mdpproject.DataClasses;
public class Vehicle {
    VehicleType Type;
    String Model,Color;


    public Vehicle(VehicleType type, String model, String color) {
        Type = type;
        Model = model;
        Color = color;
    }

    public Vehicle(String model, String color) {
        this.Type=VehicleType.SEDAN;
        Model = model;
        Color = color;
    }

    public VehicleType getType() {
        return Type;
    }

    public void setType(VehicleType type) {
        Type = type;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
