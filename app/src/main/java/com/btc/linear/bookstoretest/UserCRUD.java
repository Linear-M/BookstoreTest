package com.btc.linear.bookstoretest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Linear on 12/11/2017.
 */

public class UserCRUD {
    private DatabaseHelper databaseHelper;

    public UserCRUD(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long addUser(User user) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(User.KEY_Username, user.username);
        contentValues.put(User.KEY_Email, user.email);
        contentValues.put(User.KEY_Password, user.password);
        contentValues.put(User.KEY_Location, user.location);
        contentValues.put(User.KEY_Referral_Code, user.referral);

        return database.insert(User.TABLE, null, contentValues);
    }

    public ArrayList<User> getListOfUsers() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        //Verbose query tbh
        String getUserSelectQuery = "SELECT "
                + User.KEY_ID + ", "
                + User.KEY_Username + ", "
                + User.KEY_Email + ", "
                + User.KEY_Location + ", "
                + User.KEY_Referral_Code + ", "
                + User.KEY_Password
                + " FROM " + User.TABLE;

        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = database.rawQuery(getUserSelectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                userList.add(new User(Integer.parseInt(cursor.getString(cursor.getColumnIndex(User.KEY_ID)).toString()), cursor.getString(cursor.getColumnIndex(User.KEY_Username)),
                        cursor.getString(cursor.getColumnIndex(User.KEY_Email)), cursor.getString(cursor.getColumnIndex(User.KEY_Password)), cursor.getString(cursor.getColumnIndex(User.KEY_Location)),
                        cursor.getString(cursor.getColumnIndex(User.KEY_Referral_Code))));
            } while (cursor.moveToNext());

            cursor.close();
            database.close();
        }
        return userList;
    }

    public String getUserPassword(String username) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT " + User.KEY_Password + " FROM " + User.TABLE + " WHERE " + User.KEY_Username + "=?";
        Cursor cursor = database.rawQuery(getUserPasswordQuery, new String[]{username});

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(User.KEY_Password));
        }
        return null;
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT * FROM " + User.TABLE + " WHERE " + User.KEY_Username + "=?";
        Cursor cursor = database.rawQuery(getUserPasswordQuery, new String[]{username});

        if (cursor.moveToFirst()) {
            return new User(Integer.parseInt(cursor.getString(cursor.getColumnIndex(User.KEY_ID))), cursor.getString(cursor.getColumnIndex(User.KEY_Username)), cursor.getString(cursor.getColumnIndex(User.KEY_Email)),
                    cursor.getString(cursor.getColumnIndex(User.KEY_Password)), cursor.getString(cursor.getColumnIndex(User.KEY_Location)), cursor.getString(cursor.getColumnIndex(User.KEY_Referral_Code)));
        }
        return null;
    }
}
