package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Ben on 05/05/2018.
 */
public class Availability {
    public static final String TABLE = "Availability";

    public static final String KEY_ID = "availability_id";
    public static final String KEY_AVAILABILITY_TYPE = "availability_type";

    public int availability_ID;
    public String availabilityType;

    public Availability(String Type){
        this.availabilityType = Type;
    }
}
