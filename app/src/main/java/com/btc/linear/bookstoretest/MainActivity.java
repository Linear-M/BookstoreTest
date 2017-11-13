package com.btc.linear.bookstoretest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Bookstore Test");
    }

    public void onClick(View view) {
        //If the create button has been pressed, parse user/email/password into new user object and attempt to add user
        if (view == findViewById(R.id.btnCreate)) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            //If the show users button has been pressed (debug) display info on each user in the database
        } else if (view == findViewById(R.id.btnShowUsers)) {
            UserCRUD userCRUD = new UserCRUD(this);
            ArrayList<User> userList = userCRUD.getListOfUsers();
            if (userList.size() > 0) {
                for (User user : userList) {
                    Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();
                }
            }
            //If the login button has been pressed, find password of the account associated with the given username and compare
        } else if (view == findViewById(R.id.btnLogin)) {
            UserCRUD userCRUD = new UserCRUD(this);
            String proposedUsername = ((EditText) findViewById(R.id.txtUsername)).getText().toString();
            String providedPassword = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

            if (providedPassword.equals(userCRUD.getUserPassword(proposedUsername))) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
                this.setTitle("Welcome to Bookstore, " + proposedUsername);
                User.loggedInUser = userCRUD.getUserByUsername(proposedUsername);
                User.isLoggedIn = true;
                startActivity(new Intent(MainActivity.this, BookHomeActivity.class));
            } else {
                Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_LONG).show();
            }
        } else if (view == findViewById(R.id.btnAddBooks)) {
            BookCRUD bookCRUD = new BookCRUD(this);
            bookCRUD.addBook(new Book(1523255, "Test Book", "Test Author", 55.29, "28/09/16", "Paperback", 5, "Available",
                    "https://about.canva.com/wp-content/uploads/sites/3/2015/01/children_bookcover.png" ));
            Toast.makeText(this, "Test Book Added Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No users stored", Toast.LENGTH_LONG).show();
        }

    }
}
