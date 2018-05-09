package com.btc.linear.bookstoretest.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.btc.linear.bookstoretest.DAO.UserCRUD;
import com.btc.linear.bookstoretest.Entities.User;
import com.btc.linear.bookstoretest.R;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Register at Bookstore");
    }

    /* Main onClick handler for all selectable widgets on the register activity */
    public void onClick(View view) {
        //If the create button has been pressed, parse user/email/password into new user object and attempt to add user
        if (view == findViewById(R.id.btnRegister)) {
            registerUser();
        }
    }

    /* Main User Registration */
    private void registerUser(){
        if (((CheckBox) findViewById(R.id.chkTermsAndConditions)).isChecked()) {
            UserCRUD userCRUD = new UserCRUD(this);
            String username = ((EditText) findViewById(R.id.txtUsername)).getText().toString();
            String email = ((EditText) findViewById(R.id.txtAdminEmail)).getText().toString();
            String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
            String repeatedPassword = ((EditText) findViewById(R.id.txtRepeatPassword)).getText().toString();
            String location = ((EditText) findViewById(R.id.txtLocation)).getText().toString();
            String referralCode = ((EditText) findViewById(R.id.txtRefferalCode)).getText().toString();

            if ((username.equals("")) || (password.equals(""))){
                Toast.makeText(this, "Please Enter a Username and Password", Toast.LENGTH_LONG).show();
            } else {
                if (password.equals(repeatedPassword)) {
                    userCRUD.addUser(new User(username, email, password, location, referralCode));
                    Toast.makeText(this, "Added New User", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    Toast.makeText(this, "Passwords Must Match", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(this, "Please Agree to the Terms and Conditions", Toast.LENGTH_LONG).show();
        }
    }
}
