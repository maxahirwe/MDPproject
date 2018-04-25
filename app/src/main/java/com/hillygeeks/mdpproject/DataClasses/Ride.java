package com.hillygeeks.mdpproject.DataClasses;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Ride {
    Vehicle Vehicle;
    Location Origin, Destination;
    String DateTime;
    Boolean Returning, ShareCost;
    Integer Capacity;
    String Provider;
    RideType Type;
    Boolean RideBooked; ///< This flag is responsible for holding information either the ride is booked or not (By default its false).


    public Ride(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        RideBooked =false;
    }

    public Ride(Vehicle vehicle, Location origin, Location destination, String datetime, Boolean returning, Boolean shareCost, Integer capacity) {
        Vehicle = vehicle;
        Origin = origin;
        Destination = destination;
        DateTime = datetime;
        Returning = returning;
        ShareCost = shareCost;
        Capacity = capacity;
        Type=RideType.Offer;
        RideBooked =false;
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
    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public RideType getType() {
        return Type;
    }

    public void setType(RideType type) {
        Type = type;
    }

    public Boolean getRideBooked() {
        return RideBooked;
    }

    public void setRideBooked(Boolean rideBooked) {
        RideBooked = rideBooked;
    }
}
