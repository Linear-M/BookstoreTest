package com.btc.linear.bookstoretest.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.btc.linear.bookstoretest.Database.DatabaseHelper;
import com.btc.linear.bookstoretest.Entities.User;

import java.util.ArrayList;

/**
 * Created by Ben on 12/11/2017.
 */

public class UserCRUD {
    private DatabaseHelper databaseHelper;

    //region Context is used for toast and instantiation
    private static Context _context;

    public UserCRUD(Context context) {
        databaseHelper = new DatabaseHelper(context);
        _context = context;
    }
    //endregion

    /* Take a user object and use a CV object to pass information to a new user record in the database */
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

    /* Check if a given user's referral code is equal to the required admin code - if so, give admin privileges, if not, ignore */
    public boolean isAdmin(User user) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String getUserReferral = "SELECT " + User.KEY_Referral_Code + " FROM " + User.TABLE + " WHERE " + User.KEY_Username + " = '" + user.username + "'";

        Cursor cursor = database.rawQuery(getUserReferral, null);

        if (cursor.moveToFirst()){
            if (cursor.getString(cursor.getColumnIndex(User.KEY_Referral_Code)).equals("admin")){
                return true;
            }
        }
        return false;
    }

    /* Retrieve a list of all users in the database */
    public ArrayList<User> getListOfUsers() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
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

    /* Return the password of a user given their username */
    public String getUserPassword(String username) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        String getUserPasswordQuery = "SELECT " + User.KEY_Password + " FROM " + User.TABLE + " WHERE " + User.KEY_Username + "=?";
        Cursor cursor = database.rawQuery(getUserPasswordQuery, new String[]{username});

        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(User.KEY_Password));
        }
        return null;
    }

    /* As we assume username's are unique, return a user object based upon the given username */
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
