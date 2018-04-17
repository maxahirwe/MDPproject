package com.hillygeeks.mdpproject;

import java.util.Date;

public class RequestRide {

    Date fromDateTime;  ///< A user may set range let say, if user wants to request ride from 01/01/2018 to 05/01/2018
    Date toDateTime;

    public RequestRide(){

    }

    public Date getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Date fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Date getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Date toDateTime) {
        this.toDateTime = toDateTime;
    }
}
