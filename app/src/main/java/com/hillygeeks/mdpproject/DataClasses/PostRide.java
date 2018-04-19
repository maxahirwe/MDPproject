package com.hillygeeks.mdpproject.DataClasses;

import com.google.firebase.database.Exclude;
import com.hillygeeks.mdpproject.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostRide extends Ride {

    private String depDateTime;
    private Double price;
    private Integer totalSeats;
    private Vehicle vehicle;
    private final String FMT_DATE_TIME="MM/dd/yyyy HH:mm:ss aa";

    public PostRide(){

    }

    public PostRide(final User user){
        super(user);
    }

    public String getDepDateTime() {

        return depDateTime;
    }

    public void setDepDateTime(Date depDateTime) {
        DateFormat dateFormat = new SimpleDateFormat(FMT_DATE_TIME);
        this.depDateTime=dateFormat.format(depDateTime.getTime());
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }


}
