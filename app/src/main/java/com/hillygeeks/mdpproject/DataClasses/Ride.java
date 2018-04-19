package com.hillygeeks.mdpproject.DataClasses;

public class Ride {
    private String fromLocation;
    private String toLocation;
    private String description;
    private User user;

    public Ride() {
    }

    public Ride(final User user) {
        this.user=user;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
