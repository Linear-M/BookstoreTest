package com.btc.linear.bookstoretest.Entities;

/**
 * Created by Linear on 12/11/2017.
 */

public class User {
    public static final String TABLE = "User";

    public static final String KEY_ID = "user_id";
    public static final String KEY_Username = "username";
    public static final String KEY_Email = "user_email";
    public static final String KEY_Password = "user_password";
    public static final String KEY_Location = "user_location";
    public static final String KEY_Referral_Code = "user_referral_code";

    public int user_ID;
    public String username;
    public String email;
    public String password;
    public String location;
    public String referral;

    public static boolean isLoggedIn = false;
    public static User loggedInUser;

    /* Constructor used for making a new User when adding to the database */
    public User(String Username, String Email, String Password, String Location, String Referral) {
        username = Username;
        email = Email;
        password = Password;
        location = Location;
        referral = Referral;
    }

    /* Constructor for viewing users, and all other operations */
    public User(int UserID, String Username, String Email, String Password, String Location, String Referral) {
        user_ID = UserID;
        username = Username;
        email = Email;
        password = Password;
        location = Location;
        referral = Referral;
    }

    /* Generic toString for viewing user data cleanly */
    @Override
    public String toString() {
        return "ID: " + user_ID + "\nName: " + username + "\nEmail: " + email + "\nPassword: " + password + "\nLocation: " + location + "\nReferral Code: " + referral;
    }

}
