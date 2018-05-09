package com.btc.linear.bookstoretest.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;

import com.btc.linear.bookstoretest.DAO.BookCRUD;
import com.btc.linear.bookstoretest.DAO.ExtraCRUD;
import com.btc.linear.bookstoretest.Entities.Book;
import com.btc.linear.bookstoretest.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AdminControlPanel extends Activity {

    /* Public objects that will be instantiated onCreate() */
    ImageView bookCoverImageView;
    EditText bookName, bookPrice, releaseDate, customerReview, bookFormats, bookAvailability, bookGenre, bookAuthor, bookISBN;
    Spinner searchSpinner;
    String recentImageUri;
    SearchView searchView;

    /* On app create, instantiate UI objects by ID as well as set general listeners */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control_panel);
        this.setTitle("Bookstore's Admin Control Panel");

        bookISBN = findViewById(R.id.txtBookISBN);
        bookFormats = findViewById(R.id.txtBookFormats);
        bookAvailability = findViewById(R.id.txtBookAvailability);
        bookGenre = findViewById(R.id.txtBookGenre);
        bookAuthor = findViewById(R.id.txtAuthorName);
        customerReview = findViewById(R.id.txtCustomerReview);
        releaseDate = findViewById(R.id.txtReleaseDate);
        bookPrice = findViewById(R.id.txtPrice);
        bookName = findViewById(R.id.txtBookName);
        bookCoverImageView = findViewById(R.id.imgvBookCover);
        searchView = findViewById(R.id.srchBook);
        searchSpinner = findViewById(R.id.spnrSearchResults);

        /* Load default ACP image */
        Picasso.with(getApplicationContext()).load(Uri.parse("http://miriadna.com/desctopwalls/images/max/Milky-way.jpg")).into(bookCoverImageView);

        /* After a search, if a different ISBN is selected re-filter results */
        searchSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                fillBookInformationFromSearch((long)parent.getItemAtPosition(pos));
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //Test nothing selected but fired
            }
        });

        /* When a search is requested, generate an array adapter filled with relevant book information an attach to the ISBN selector spinner */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String searchCriteria) {
                ArrayAdapter<Long> spinnerArrayAdapter = new ArrayAdapter<>(AdminControlPanel.this, R.layout.spinner_item, searchBooks(searchCriteria));
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                searchSpinner.setAdapter(spinnerArrayAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    /* Main onClick handler for the class, differentiates between button-clicks */
    public void onClick(View view) {
        if (view == findViewById(R.id.btnAddRandomBook)) {
            addRandomBook();
            Toast.makeText(this, "Randomly-Generated Book Added Successfully", Toast.LENGTH_SHORT).show();
        } else if (view == findViewById(R.id.btnDeleteBook)){
            deleteBook();
            Toast.makeText(this, "Book Deleted", Toast.LENGTH_SHORT).show();
        } else if (view == findViewById(R.id.imgvBookCover)){
            bookCoverClickHandler();
        } else if (view == findViewById(R.id.btnSaveChanges)){
            saveChanges();
        } else if (view == findViewById(R.id.btnAddBook)){
            addBook();
        } else if (view == findViewById(R.id.btnMoreOptions)){
            startActivity(new Intent(this, MoreOptions.class));
        }
    }

    /* Fetch information from widgets, instantiate new book and use the DAO to amend database */
    private void addBook(){
        if ((searchSpinner.getSelectedItem() == null)) {
            if ((bookISBN.getText().toString().equals(""))){
                Toast.makeText(this, "No Details Provided", Toast.LENGTH_LONG).show();
            } else {
                BookCRUD bookCRUD = new BookCRUD(this);
                ExtraCRUD extraCRUD = new ExtraCRUD(this);
                Random rng = new Random();

                long _ISBN = Long.valueOf(bookISBN.getText().toString());
                String _bookName = bookName.getText().toString();
                double _price = Double.valueOf(bookPrice.getText().toString());
                int _customerReview = Integer.valueOf(customerReview.getText().toString());
                String _pictureURL = recentImageUri;
                String _releaseDate = releaseDate.getText().toString();
                String _bookFormats = bookFormats.getText().toString();
                String _bookAvailability = bookAvailability.getText().toString();
                String _bookAuthor = bookAuthor.getText().toString();
                String _description = bookAvailability.getText().toString();
                String _genre = bookGenre.getText().toString();

                if (extraCRUD.isRelationalDataCorrect(_bookAuthor, _bookAvailability, _bookFormats, _genre)) {
                    bookCRUD.addBook(new Book(_ISBN, _bookName, _bookAuthor, _price, _releaseDate, _bookFormats, _customerReview, _bookAvailability, _pictureURL, _description, _genre));
                    Toast.makeText(this, "Book Added", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Cannot Add Book to Database - Book Exists Already", Toast.LENGTH_LONG).show();
        }
    }

    /* Fetch the ISBN of the book to be deleted and use DAO methods to remove the book from the database and reset the view */
    private void deleteBook(){
        if (!bookISBN.getText().toString().equals("")) {
            //Delete book
            BookCRUD bookCRUD = new BookCRUD(this);
            bookCRUD.deleteBook(Long.valueOf(searchSpinner.getSelectedItem().toString()));
            //Reset view
            startActivity(new Intent(AdminControlPanel.this, AdminControlPanel.class));
        } else {
            Toast.makeText(this, "No Book Present to Delete", Toast.LENGTH_LONG).show();
        }
    }

    /* Generate a random book object with random ISBN's and costs (default other data such as book name, author and description) */
    private void addRandomBook(){
        BookCRUD bookCRUD = new BookCRUD(this);
        Random rng = new Random();
        bookCRUD.addBook(new Book((rng.nextInt(1000000000) + 1000000000), "Test Book", "Test Author", rng.nextInt(10) + 1, "28/09/16", "Paperback",
                5, "Available", "https://about.canva.com/wp-content/uploads/sites/3/2015/01/children_bookcover.png",
                "Test Description", "SampleGenre"));
    }

    /* Using the book DAO retrieve a list of books objects that match a given search criteria */
    private List<Long> searchBooks(String searchCriteria){
        BookCRUD bookCRUD = new BookCRUD(this);
        Cursor cursor = bookCRUD.getListOfSearchedBooks(searchCriteria);
        List<Long> tempList = new ArrayList<>();

        Toast.makeText(this, "Searching for '" + searchCriteria + "'", Toast.LENGTH_SHORT).show();

        if (cursor.moveToFirst()){
            do{
                tempList.add(cursor.getLong(cursor.getColumnIndex(Book.KEY_ISBN)));
            } while(cursor.moveToNext());
        }
        return tempList;
    }

    /* Use a returned book list to set book information text widget's in the ACP */
    private void fillBookInformationFromSearch(long ISBN){
        //Get selected book information
        BookCRUD bookCRUD = new BookCRUD(this);
        Book returnedBook = bookCRUD.getBookByISBN(ISBN);

        //Currency formatter (local must be UK)
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
        String moneyString = formatter.format(returnedBook.getPrice());



        //Fill information fields
        try{
            Picasso.with(getApplicationContext()).load(Uri.parse(returnedBook.getPictureUrl())).into(bookCoverImageView);
        }catch (Exception e){
            Log.d("ERR", e.getMessage());
            Picasso.with(getApplicationContext()).load(Uri.parse("https://bit.ly/2KE2Jvz")).into(bookCoverImageView);
        }

        bookISBN.setText(String.valueOf(returnedBook.getISBN()));
        bookFormats.setText(String.valueOf(returnedBook.getFormat()));
        bookAvailability.setText(String.valueOf(returnedBook.getAvailability()));
        bookGenre.setText(String.valueOf(returnedBook.getGenre()));
        bookAuthor.setText(String.valueOf(returnedBook.getAuthor()));
        bookPrice.setText(String.valueOf(moneyString));
        customerReview.setText(String.valueOf(returnedBook.getReview()));
        releaseDate.setText(String.valueOf(returnedBook.getReleaseDate()));
        bookName.setText(String.valueOf(returnedBook.getName()));
    }

    /* When the book cover image is clicked, generate a modal dialog (alert dialog) that will allow for a new book cover image URL to be added */
    private void bookCoverClickHandler(){
        final EditText txtUrl = new EditText(this);
        final BookCRUD bookCRUD = new BookCRUD(this);

        //Default link is 'default' or current book cover
        txtUrl.setHint("https://www.link.to/image.jpg");

        //Alert dialog builder is great here to set custom title/message as well as procedurally generating the handlers with custom code
        new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Widget_Material_ButtonBar_AlertDialog))
                .setTitle("Book Cover Image")
                .setMessage("Paste in the link of the new book cover image")
                .setView(txtUrl)
                //If 'save' is clicked, use DAO to save the new book cover image URL
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        try{
                            bookCRUD.updateBookCoverURL(txtUrl.getText().toString(), Long.valueOf(searchSpinner.getSelectedItem().toString()));
                        } catch (Exception e){}
                        recentImageUri = txtUrl.getText().toString();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Cancelled cover update
                    }
                })
                .show();
    }

    /* Obtain the new - and unchanged - book information from the widgets and use the book DAO to update the selected book's attributes */
    private void saveChanges(){
        if (searchSpinner.getSelectedItem() != null) {
            final BookCRUD bookCRUD = new BookCRUD(this);
            final ExtraCRUD extraCRUD = new ExtraCRUD(this);
            double _bookPrice;

            long _ISBN = Long.valueOf(bookISBN.getText().toString());
            String _bookFormats = bookFormats.getText().toString();
            String _bookAvailability = bookAvailability.getText().toString();
            String _bookAuthor = bookAuthor.getText().toString();

            //Try to parse the currency string back to a double as requested by the DAO
            try {
                _bookPrice = Double.valueOf(DecimalFormat.getCurrencyInstance(Locale.UK).parse((bookPrice.getText().toString())).toString());
            } catch (ParseException e) {
                return;
            }

            int _customerReview = Integer.valueOf(customerReview.getText().toString());
            String _releaseDate = releaseDate.getText().toString();
            String _bookName = bookName.getText().toString();
            String _bookGenre = bookGenre.getText().toString();

            if (extraCRUD.isRelationalDataCorrect(_bookAuthor, _bookAvailability, _bookFormats, _bookGenre)) {
                bookCRUD.updateBook(_ISBN, _bookFormats, _bookAvailability, _bookAuthor, _bookPrice, _customerReview, _releaseDate, _bookName, _bookGenre);
            }
        } else {
            Toast.makeText(this, "Cannot Save Changes - No Book Loaded", Toast.LENGTH_LONG).show();
        }
    }
}
