package com.btc.linear.bookstoretest.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.btc.linear.bookstoretest.DAO.UserCRUD;
import com.btc.linear.bookstoretest.Entities.User;
import com.btc.linear.bookstoretest.R;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Bookstore©");
    }

    /* 'Start where we left off' */
    @Override
    public void onResume() {
        super.onResume();
    }

    /* Main onClick handler for all selectable widgets on the main welcome/login activity */
    public void onClick(View view) {
        //If the create button has been pressed, parse user/email/password into new user object and attempt to add user
        if (view == findViewById(R.id.btnCreate)) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            //If the show users button has been pressed (debug) display info on each user in the database
        } else if (view == findViewById(R.id.btnAddRandomBook)) {
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
        }
    }
}
