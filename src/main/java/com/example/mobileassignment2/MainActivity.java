package com.example.mobileassignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Initialize the input texts and buttons  that need to be accessed later
    EditText  latitude;
    EditText longitude;
    EditText addressText;
    Button searchButton,insertButton,locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latInsert);
        longitude = findViewById(R.id.longInsert);
        addressText = findViewById(R.id.inputAddress);

        searchButton =findViewById(R.id.searchButton );
        locationButton = findViewById(R.id.locationButton);
        insertButton = findViewById(R.id. insertButton);


        locationDatabase database = new locationDatabase(this);
        //this.deleteDatabase("location.db");

        //Insert the initial database values (addresses are generated using geocoding)
        //before storing in database
        insertToDatabase(1.384127, 103.868290);
        insertToDatabase(48.858372, 2.294481);
        insertToDatabase( 43.7428, -79.20462);
        insertToDatabase( 43.90012, -78.84957);
        insertToDatabase( 51.90798, -8.59724);
        insertToDatabase( 51.90125, -8.60142);
        insertToDatabase( 38.9117,-77.01017 );
        insertToDatabase( 38.91194, -77.01015);
        insertToDatabase(38.9117 , -77.01017);
        insertToDatabase( 38.91155, -77.01035);

        //Search the database for the inputted Address, will display the latitude and longitudes
        //using a toast message to the user
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addressText = view.findViewById(R.id.inputAddress);
                String address = addressText.getText().toString();

                locationDatabase database = new locationDatabase(MainActivity.this);
                Cursor thisAddress = database.getAddressData(address);
                @SuppressLint("Range") String thisLat = thisAddress.getString(thisAddress.getColumnIndex("Latitude"));
                @SuppressLint("Range") String thisLong = thisAddress.getString(thisAddress.getColumnIndex("Longitude"));
                Toast myToast = Toast.makeText(MainActivity.this,"Latitude: "+thisLat +" "+"Longitude: "+thisLong,Toast.LENGTH_SHORT);
                myToast.show();
            }
        });


        //Use the inputted Address to generate appropriate coordinates using geocoding,
        //then insert the values into the location table in the database
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDatabase database = new locationDatabase(MainActivity.this);

                String theAddress = addressText.getText().toString();

                List<Address> list;
                Geocoder gcd = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    list = gcd.getFromLocationName(theAddress, 1);
                    Address thisAddress= list.get(0);
                    double latitude = thisAddress.getLatitude();
                    double longitude = thisAddress.getLongitude();
                    database.insertLocation(thisAddress.getAddressLine(0),latitude,longitude);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Test button to try inserting coordinates to the database
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDatabase database = new locationDatabase(MainActivity.this);
                insertToDatabase(43.7428, -79.20462);

            }
        });
    }

    //Function that takes the coordinates and generates the address using geocoding
    //then inserts the values into the location table in the database
    private void insertToDatabase(double latitude, double longitude){
        locationDatabase database = new locationDatabase(this);

        List<Address> list;
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        try {
            list = gcd.getFromLocation(latitude, longitude, 1);
            String address = list.get(0).getAddressLine(0);

            database.insertLocation(address,latitude, longitude);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}