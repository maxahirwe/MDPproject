package com.hillygeeks.mdpproject.DataClasses;

import android.support.annotation.NonNull;
import com.google.firebase.database.IgnoreExtraProperties;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@IgnoreExtraProperties
public class Ride implements Comparable<Ride> {
    public String id;
    Vehicle vehicle;
    Location origin, destination;
    String saved_dateTime;
    String depart_datetime;
    String return_datetime;
    Boolean returning, shareCost, booked;
    Integer capacity;
    String creator;
    RideType type;
    // the user id of the person getting the ride
    String client;
    // the user id of the person offering the ride
    String provider;



    public Ride(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Ride(Vehicle vehicle, Location origin, Location destination, String depart_datetime,String return_datetime, Boolean returning, Boolean shareCost, Integer capacity) {
        this.vehicle = vehicle;
        this.origin = origin;
        this.destination = destination;
        this.saved_dateTime =new SimpleDateFormat("MM-dd-yyyy HH:mm").format(new Date());
        this.depart_datetime = depart_datetime;
        this.return_datetime = return_datetime;
        this.returning = returning;
        this.shareCost = shareCost;
        this.capacity = capacity;
        this.type =RideType.Offer;
        this.booked=false;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public String getSaved_dateTime() {
        return saved_dateTime;
    }

    public void setSaved_dateTime(String saved_dateTime) {
        this.saved_dateTime = saved_dateTime;
    }

    public String getDepart_datetime() {
        return depart_datetime;
    }

    public void setDepart_datetime(String depart_datetime) {
        this.depart_datetime = depart_datetime;
    }

    public String getReturn_datetime() {
        return return_datetime;
    }

    public void setReturn_datetime(String return_datetime) {
        this.return_datetime = return_datetime;
    }

    public Boolean getReturning() {
        return returning;
    }

    public void setReturning(Boolean returning) {
        this.returning = returning;
    }

    public Boolean getShareCost() {
        return shareCost;
    }

    public void setShareCost(Boolean shareCost) {
        this.shareCost = shareCost;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public RideType getType() {
        return type;
    }

    public void setType(RideType type) {
        this.type = type;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", saved_dateTime='" + saved_dateTime + '\'' +
                ", returning=" + returning +
                ", shareCost=" + shareCost +
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

