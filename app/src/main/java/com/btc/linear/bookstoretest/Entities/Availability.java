package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Ben on 05/05/2018.
 */

/* Availability class - relational supporting class to the book class */
public class Availability {
    public static final String TABLE = "Availability";

    public static final String KEY_ID = "availability_id";
    public static final String KEY_AVAILABILITY_TYPE = "availability_type";

    private int availability_ID;
    private String availabilityType;

    public int getAvailability_ID() {
        return availability_ID;
    }

    public void setAvailability_ID(int availability_ID) {
        this.availability_ID = availability_ID;
    }

    public String getAvailabilityType() {
        return availabilityType;
    }

    public void setAvailabilityType(String availabilityType) {
        this.availabilityType = availabilityType;
    }

    /* Constructor to instantiate new object with data */
    public Availability(String Type){
        this.availabilityType = Type;
    }
}
