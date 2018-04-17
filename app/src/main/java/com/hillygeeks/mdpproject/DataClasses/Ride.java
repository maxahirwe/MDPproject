package com.hillygeeks.mdpproject.DataClasses;

import java.time.LocalDateTime;

public class Ride {
    Vehicle Vehicle;
    Location Origin, Destination;
    LocalDateTime Time;
    Boolean Returning, ShareCost;
    Integer Capacity;

    public Ride(Vehicle vehicle, Location origin, Location destination, LocalDateTime time, Boolean returning, Boolean shareCost, Integer capacity) {
        Vehicle = vehicle;
        Origin = origin;
        Destination = destination;
        Time = time;
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

    public LocalDateTime getTime() {
        return Time;
    }

    public void setTime(LocalDateTime time) {
        Time = time;
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
