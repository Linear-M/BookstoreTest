package com.btc.linear.bookstoretest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Register at Bookstore");
    }

    public void onClick(View view) {
        //If the create button has been pressed, parse user/email/password into new user object and attempt to add user
        if (view == findViewById(R.id.btnRegister)) {
            if (((CheckBox) findViewById(R.id.chkTermsAndConditions)).isChecked()) {
                UserCRUD userCRUD = new UserCRUD(this);
                String username = ((EditText) findViewById(R.id.txtUsername)).getText().toString();
                String email = ((EditText) findViewById(R.id.txtEmail)).getText().toString();
                String password = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
                String repeatedPassword = ((EditText) findViewById(R.id.txtPassword)).getText().toString();
                String location = ((EditText) findViewById(R.id.txtLocation)).getText().toString();
                String referralCode = ((EditText) findViewById(R.id.txtRefferalCode)).getText().toString();

                if (password.equals(repeatedPassword)) {
                    User user = new User(username, email, password, location, referralCode);
                    userCRUD.addUser(user);
                    Toast.makeText(this, "Added New User", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Passwords Must Match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please Agree to the Terms and Conditions", Toast.LENGTH_LONG).show();
            }
        }
    }
}
