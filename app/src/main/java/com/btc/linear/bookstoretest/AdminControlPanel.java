package com.btc.linear.bookstoretest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class AdminControlPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_panel);
        this.setTitle("Bookstore's Admin Control Panel");
        View seperator = new View(this);
        LinearLayout.LayoutParams bookInfoParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        );
        bookInfoParams.setMargins(0,16,0,16);
        seperator.setLayoutParams(bookInfoParams);
        seperator.setBackgroundColor(Color.LTGRAY);
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnLogin)) {
            BookCRUD bookCRUD = new BookCRUD(this);
            Random rng = new Random();
            bookCRUD.addBook(new Book((rng.nextInt(2000000000) + 1000000000), "Test Book", "Test Author", 55.29, "28/09/16", "Paperback",
                    5, "Available", "https://about.canva.com/wp-content/uploads/sites/3/2015/01/children_bookcover.png",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer turpis velit, malesuada vitae lorem quis, ultrices tempor sem. Proin tristique mauris ante," +
                            " ut pharetra magna pulvinar non."));
            Toast.makeText(this, "Test Book Added Successfully", Toast.LENGTH_SHORT).show();
        } else if (view == findViewById(R.id.btnShowUsers)) {
            UserCRUD userCRUD = new UserCRUD(this);
            ArrayList<User> userList = userCRUD.getListOfUsers();
            if (userList.size() > 0) {
                for (User user : userList) {
                    Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();
                }
            }
        } else if (view == findViewById(R.id.btnAddAdminUser)){
            UserCRUD userCRUD = new UserCRUD(this);
            String username = ((EditText) findViewById(R.id.txtAdminUser)).getText().toString();
            String email = ((EditText) findViewById(R.id.txtAdminEmail)).getText().toString();
            String password = ((EditText) findViewById(R.id.txtAdminPassword)).getText().toString();
            String repeatedPassword = password;
            String location = "ADMIN_CREATED";
            String referralCode = "123456";
            try{
                userCRUD.addUser(new User(username, email, password, location, referralCode));
                Toast.makeText(this, "Added New User", Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                Log.d("AddAdminUser", e.getMessage());
                Toast.makeText(this, "Error Adding User", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
