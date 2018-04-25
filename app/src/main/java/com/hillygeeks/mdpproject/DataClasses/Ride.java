package com.hillygeeks.mdpproject.DataClasses;

import android.support.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@IgnoreExtraProperties
public class Ride implements Comparable<Ride> {
    public String id;
    Vehicle Vehicle;
    Location Origin, Destination;
    String Saved_dateTime;
    String Depart_datetime;
    String Return_datetime;
    Boolean Returning, ShareCost,Booked;
    Integer Capacity;
    String creator;
    RideType Type;
    // the user id of the person getting the ride
    String Client;
    // the user id of the person offering the ride
    String Provider;


    public Ride(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Ride(Vehicle vehicle, Location origin, Location destination, String depart_datetime,String return_datetime, Boolean returning, Boolean shareCost, Integer capacity) {
        Vehicle = vehicle;
        Origin = origin;
        Destination = destination;
        Saved_dateTime=new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date());
        Depart_datetime = depart_datetime;
        Return_datetime = return_datetime;
        Returning = returning;
        ShareCost = shareCost;
        Capacity = capacity;
        Type=RideType.Offer;
        Booked=false;
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

    public String getSaved_dateTime() {
        return Saved_dateTime;
    }

    public void setSaved_dateTime(String saved_dateTime) {
        Saved_dateTime = saved_dateTime;
    }

    public String getDepart_datetime() {
        return Depart_datetime;
    }

    public void setDepart_datetime(String depart_datetime) {
        Depart_datetime = depart_datetime;
    }

    public String getReturn_datetime() {
        return Return_datetime;
    }

    public void setReturn_datetime(String return_datetime) {
        Return_datetime = return_datetime;
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
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public RideType getType() {
        return Type;
    }

    public void setType(RideType type) {
        Type = type;
    }

    public Boolean getBooked() {
        return Booked;
    }

    public void setBooked(Boolean booked) {
        Booked = booked;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", Saved_dateTime='" + Saved_dateTime + '\'' +
                ", Returning=" + Returning +
                ", ShareCost=" + ShareCost +
                '}';
    }

    @Override
    public int compareTo(@NonNull Ride ride) {
        SimpleDateFormat format=new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(this.getSaved_dateTime());
            date2 = format.parse( ride.getSaved_dateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.compareTo(date2)*-1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return this.id.equalsIgnoreCase(ride.id);
    }
}

