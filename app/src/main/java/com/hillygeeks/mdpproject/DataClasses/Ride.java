package com.hillygeeks.mdpproject.DataClasses;

import com.google.firebase.database.IgnoreExtraProperties;

import java.time.LocalDateTime;

@IgnoreExtraProperties
public class Ride {
    Vehicle Vehicle;
    Location Origin, Destination;
    String DateTime;
    Boolean Returning, ShareCost;
    Integer Capacity;

    public Ride(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Ride(Vehicle vehicle, Location origin, Location destination, String datetime, Boolean returning, Boolean shareCost, Integer capacity) {
        Vehicle = vehicle;
        Origin = origin;
        Destination = destination;
        DateTime = datetime;
        Returning = returning;
        ShareCost = shareCost;
        Capacity = capacity;
    }

    public Vehicle getVehicle() {
        return Vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        Vehicle = vehicle;
    }

    public Location getOrigin() {
        return Origin;
    }

    public void setOrigin(Location origin) {
        Origin = origin;
    }

    public Location getDestination() {
        return Destination;
    }

    public void setDestination(Location destination) {
        Destination = destination;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public Boolean getReturning() {
        return Returning;
    }

    public void setReturning(Boolean returning) {
        Returning = returning;
    }

    public Boolean getShareCost() {
        return ShareCost;
    }

    public void setShareCost(Boolean shareCost) {
        ShareCost = shareCost;
    }

    public Integer getCapacity() {
        return Capacity;
    }

    public void setCapacity(Integer capacity) {
        Capacity = capacity;
    }
}
