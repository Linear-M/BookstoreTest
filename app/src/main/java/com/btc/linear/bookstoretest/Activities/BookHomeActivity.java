package com.btc.linear.bookstoretest.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;


import com.btc.linear.bookstoretest.DAO.BookCRUD;
import com.btc.linear.bookstoretest.DAO.ExtraCRUD;
import com.btc.linear.bookstoretest.DAO.UserCRUD;
import com.btc.linear.bookstoretest.Entities.Availability;
import com.btc.linear.bookstoretest.Entities.Book;
import com.btc.linear.bookstoretest.Entities.Format;
import com.btc.linear.bookstoretest.Entities.Genre;
import com.btc.linear.bookstoretest.Entities.User;
import com.btc.linear.bookstoretest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookHomeActivity extends Activity {

    /* Public widget objects */
    Spinner spinner, advancedSpinner;
    Button logoutButton;
    View linearLayout;

    /* If the user selects the default 'back' android button, check for admin status - if true, load ACP, if not, tell them how to logout manually */
    @Override
    public void onBackPressed() {
        //Do nothing
        UserCRUD userCRUD = new UserCRUD(this);
        if (userCRUD.isAdmin(User.loggedInUser)){
            //Load ACP
            Toast.makeText(BookHomeActivity.this, "ADMIN CONFIRMED - LOADING ACP", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BookHomeActivity.this, AdminControlPanel.class));
        } else {
            Toast.makeText(BookHomeActivity.this, "To go back, please logout", Toast.LENGTH_SHORT).show();
        }
    }

    /* As creation of this activity is largely procedural there is signficiant design code - effectively generates a list of books with their image and basic information available */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BookCRUD bookCRUD = new BookCRUD(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_home);
        this.setTitle(User.loggedInUser.getUsername() + "'s Bookstore");

        //Find/instantiate widget objects
        spinner = (Spinner) findViewById(R.id.spnrFilter) ;
        advancedSpinner = (Spinner)findViewById(R.id.spnrAdvancedFilter);
        linearLayout = findViewById(R.id.lytInfo);
        logoutButton = findViewById(R.id.btnLogout);

        //Create basic spinner filter list
        java.util.ArrayList<String> strings = new java.util.ArrayList<>();
        strings.add("Price") ;
        strings.add("Availability");
        strings.add("Format");
        strings.add("Genre");

        /* Create spinner adapter(s) from hardcoded information, and then assign click/select event handlers */
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);

        //When 'main' spinner item changes refresh the sub-filer spinner lists
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selectAdvancedFilters(parent.getItemAtPosition(pos).toString(), advancedSpinner);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //Test nothing selected but fired
            }
        });

        //When 'sub-filter' spinner item changes generate a filtered/sorted book list and re-draw the activity
        advancedSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                //Filter results
                try{
                    BookCRUD bookCRUD = new BookCRUD(BookHomeActivity.this);
                    drawBookResults(bookCRUD.getFilteredListOfBooks(parent.getItemAtPosition(pos).toString(), spinner.getSelectedItem().toString()));
                } catch (Exception e){
                    Log.d("advancedSpinnerError", e.toString());
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //Test nothing selected but fired
            }
        });

        //If the logout button is selected, notify the user of logout and reset the application
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                //Logout
                Toast.makeText(BookHomeActivity.this, "Logging off", Toast.LENGTH_SHORT).show();

                User.loggedInUser = null;
                User.isLoggedIn = false;

                startActivity(new Intent(BookHomeActivity.this, MainActivity.class));
            }
        });

        //*Finally* draw the default, full, list of books
        drawBookResults(bookCRUD.getListOfBooks());
    }

     /* Iterate through the given list of books and render a view of this book:
        -------------------------------------
         BOOK_IMAGE | Book.toDisplayString()
        -------------------------------------
     */
    public void drawBookResults(ArrayList<Book> bookList){
        LinearLayout x = (LinearLayout)linearLayout;
        x.removeAllViews();

        if (bookList.size() > 0) {
            for (Book book : bookList) {
                final Book tempBook = book;
                LinearLayout sideBySide = new LinearLayout(this);
                sideBySide.setOrientation(LinearLayout.HORIZONTAL);

                //Right hand side text
                TextView bookView = new TextView(this);
                bookView.setText(book.toDisplayString());
                bookView.setId((int)book.getISBN());
                bookView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                //Left hand side book-cover image fetching, parsing and rendering
                ImageView bookCover = new ImageView(this);
                try{
                    Picasso.with(getApplicationContext()).load(Uri.parse(book.getPictureUrl())).into(bookCover);
                }catch (Exception e){
                    Picasso.with(getApplicationContext()).load(Uri.parse("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/book-cover-flyer-template-6bd8f9188465e443a5e161a7d0b3cf33_screen.jpg?ts=1456287935")).into(bookCover);
                }
                LinearLayout.LayoutParams coverParams = new LinearLayout.LayoutParams(
                        270,
                        480
                );
                coverParams.setMargins(25,-10,64,0);
                bookCover.setLayoutParams(coverParams);

                //Separating line between each book drawn-object
                View separator = new View(this);
                LinearLayout.LayoutParams bookInfoParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2
                );
                bookInfoParams.setMargins(0,16,0,16);
                separator.setLayoutParams(bookInfoParams);
                separator.setBackgroundColor(Color.LTGRAY);

                /* Build the modal/dialog with full book information that will be shown when a user clicks the individual book layout */
                bookCover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isFinishing()){
                                    new AlertDialog.Builder(BookHomeActivity.this)
                                            .setTitle(tempBook.getName() + " - " + tempBook.getAuthor())
                                            .setMessage(tempBook.toModalString())
                                            .setCancelable(false)
                                            .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            }).show();
                                }
                            }
                        });
                    }
                });

                //Add book cover and book text to separate view for formatting reasons
                sideBySide.addView(bookCover);
                sideBySide.addView(bookView);

                //Add main view and separator to the procedural layout
                ((LinearLayout)linearLayout).addView(sideBySide);
                ((LinearLayout)linearLayout).addView(separator);
            }
        }
    }

    /* Hard-coded sub/advanced filters that are generated based upon the given/selected main filter */
    public void selectAdvancedFilters(String selectedFilter, Spinner spinner){
        java.util.ArrayList<String> advancedFilterStrings = new java.util.ArrayList<>();
        try{
            switch (selectedFilter){
                case "Price":
                    advancedFilterStrings.add("Low to High");
                    advancedFilterStrings.add("High to Low");
                    break;
                case "Availability":
                    advancedFilterStrings = availabilityList();
                    break;
                case "Format":
                    advancedFilterStrings = formatList();
                    break;
                case "Genre":
                    advancedFilterStrings = genreList();
                    break;
                default:
                    break;
            }

            //Create an AA to easily convert the AL to a Spinner list
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, advancedFilterStrings);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerArrayAdapter);
        } catch (Exception e) {

        }
    }

    /* Using the extra DAO retrieve a list of genre objects */
    private ArrayList<String> genreList(){
        ExtraCRUD extraCRUD = new ExtraCRUD(this);
        Cursor cursor = extraCRUD.getListOfGenres();
        ArrayList<String> tempList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                tempList.add(cursor.getString(cursor.getColumnIndex(Genre.KEY_GENRE_TYPE)));
            } while(cursor.moveToNext());
        }
        return tempList;
    }

    /* Using the extra DAO retrieve a list of format objects */
    private ArrayList<String> formatList(){
        ExtraCRUD extraCRUD = new ExtraCRUD(this);
        Cursor cursor = extraCRUD.getListOfFormats();
        ArrayList<String> tempList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                tempList.add(cursor.getString(cursor.getColumnIndex(Format.KEY_FORMAT_TYPE)));
            } while(cursor.moveToNext());
        }
        return tempList;
    }

    /* Using the extra DAO retrieve a list of format objects */
    private ArrayList<String> availabilityList(){
        ExtraCRUD extraCRUD = new ExtraCRUD(this);
        Cursor cursor = extraCRUD.getListOfAvailabilities();
        ArrayList<String> tempList = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                tempList.add(cursor.getString(cursor.getColumnIndex(Availability.KEY_AVAILABILITY_TYPE)));
            } while(cursor.moveToNext());
        }
        return tempList;
    }
}
