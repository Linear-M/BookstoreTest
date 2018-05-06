package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Ben on 05/05/2018.
 */
public class Format {
    public static final String TABLE = "Format";

    public static final String KEY_ID = "format_id";
    public static final String KEY_FORMAT_TYPE = "format_type";

    public int format_ID;
    public String formatType;

    public Format(String Type){
        this.formatType = Type;
    }
}
