package com.hillygeeks.mdpproject;

import java.util.Date;

public class PostRide extends Ride {

    private Date departureDateTime;
    private Double price;
    private int availableSpots;


    public PostRide(){

    }



    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(Date departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }


}
