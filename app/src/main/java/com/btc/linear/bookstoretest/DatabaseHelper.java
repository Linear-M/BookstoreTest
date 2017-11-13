package com.btc.linear.bookstoretest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Linear on 12/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 11;

    public static final String DATABASE_NAME = "BookstoreTest.db";

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE + "("
                + User.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + User.KEY_Username + " TEXT, "
                + User.KEY_Email + " TEXT, "
                + User.KEY_Location + " TEXT, "
                + User.KEY_Referral_Code + " TEXT, "
                + User.KEY_Password + " TEXT )";

        String CREATE_TABLE_BOOK = "CREATE TABLE " + Book.TABLE + "("
                + Book.KEY_ISBN + " BIGINT PRIMARY KEY, "
                + Book.KEY_Name + " TEXT, "
                + Book.KEY_Author + " TEXT, "
                + Book.KEY_Price + " DECIMAL, "
                + Book.KEY_ReleaseDate + " TEXT, "
                + Book.KEY_Format + " TEXT, "
                + Book.KEY_Review + " SMALLINT, "
                + Book.KEY_PictureURL + " TEXT, "
                + Book.KEY_Description + " TEXT, "
                + Book.KEY_Availability + " TEXT )";

        database.execSQL(CREATE_TABLE_USER);
        database.execSQL(CREATE_TABLE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + Book.TABLE);

        onCreate(database);
    }
}
