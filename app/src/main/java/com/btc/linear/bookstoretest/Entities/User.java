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

    private int user_ID;
    private String username;
    private String email;
    private String password;
    private String location;
    private String referral;

    public static boolean isLoggedIn = false;
    public static User loggedInUser;

    //Region getters and setters

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public static boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        User.isLoggedIn = isLoggedIn;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    //endregion

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
