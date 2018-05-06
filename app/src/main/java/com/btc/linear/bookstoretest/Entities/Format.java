package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Ben on 05/05/2018.
 */

/* Format class - relational supporting class to the book class */
public class Format {
    public static final String TABLE = "Format";

    public static final String KEY_ID = "format_id";
    public static final String KEY_FORMAT_TYPE = "format_type";

    private int format_ID;

    private String formatType;

    public int getFormat_ID() {
        return format_ID;
    }

    public void setFormat_ID(int format_ID) {
        this.format_ID = format_ID;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    /* Constructor to instantiate new object with data */
    public Format(String Type){
        this.formatType = Type;
    }
}
