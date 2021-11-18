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


    EditText  latitude;
    EditText longitude;
    EditText addressText;
    Button searchButton,insertButton,checkButton,locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        latitude = findViewById(R.id.latInsert);
        longitude = findViewById(R.id.longInsert);
        addressText = findViewById(R.id.inputAddress);

        searchButton =findViewById(R.id.searchButton );
        locationButton = findViewById(R.id.locationButton);
        checkButton = findViewById(R.id.checkButton);
        insertButton = findViewById(R.id. insertButton);


        locationDatabase database = new locationDatabase(this);
        this.deleteDatabase("location.db");
        //database.insertLocation("second",323131.5253,23313.7747);
        //String word = database.DATABASE_NAME;

        //database.insertLocation("from on create",2,50);
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


        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDatabase database = new locationDatabase(MainActivity.this);
                //double latNumber = Double.parseDouble(latitude.getEditableText().toString());
                //String latNumber = latitude.getText().toString();

                List<Address> list;
                Geocoder gcd = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    list = gcd.getFromLocationName("1600 Amphitheatre Parkway, Mountain View, CA", 1);
                    Address lat = list.get(0);
                    String message = lat.toString();
                    Toast myToast = Toast.makeText(MainActivity.this,"Success: "+message,Toast.LENGTH_SHORT);
                    myToast.show();
                    //database.insertLocation(lat.getAddressLine(0),);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //findAddress(view);


            }
        });


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latNumber = latitude.getText().toString();
                //double longNumber = Double.parseDouble(longitude.getText().toString());
                Toast another = Toast.makeText(MainActivity.this,"Success: " +latNumber,Toast.LENGTH_SHORT);
                another.show();
                /*
                double latNumber = Double.parseDouble(latitude.getText().toString());
                double longNumber = Double.parseDouble(longitude.getText().toString());
                lat = latitude.getText().toString();
                //longi = longitude.getText().toString().trim();

                Toast another = Toast.makeText(getActivity(),"Success: " +lat,Toast.LENGTH_SHORT);
                another.show();

                 */

            }
        });


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationDatabase database = new locationDatabase(MainActivity.this);
                //getContext().deleteDatabase("location.db");
                //database.insertLocation("second",323131.5253,23313.7747);
                //String word = database.DATABASE_NAME;

                database.insertLocation("example2",2,50);
                //insertToDatabase(1.384127, 103.868290);
                //insertToDatabase(48.858372, 2.294481);
                insertToDatabase(43.7428, -79.20462);
                /*
                List<Address> list;
                Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());

                try {
                    //list = gcd.getFromLocation(1.384127, 103.868290, 1); //singapore
                    list = gcd.getFromLocation(	48.858372, 2.294481, 1); //paris
                    //list = gcd.getFromLocation(40.65833044834671, -73.99418920683213, 1);

                    //String message = list.get(0).getAddressLine(0);  //one i need
                    //String message = list.get(0).toString();

                    //Toast myToast = Toast.makeText(getActivity(),"Success: "+message,Toast.LENGTH_LONG);
                    //myToast.show();
                    //database.insertLocation(message,48.858372, 2.294481);
                    insertToDatabase(1.384127, 103.868290);
                    //SystemClock.sleep(2000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */


            }
        });
    }

    private void insertToDatabase(double latitude, double longitude){
        locationDatabase database = new locationDatabase(this);
        //getContext().deleteDatabase("location.db");
        //database.insertLocation("second",323131.5253,23313.7747);
        //String word = database.DATABASE_NAME;

        List<Address> list;
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        try {
            //list = gcd.getFromLocation(1.384127, 103.868290, 1); //singapore
            //list = gcd.getFromLocation(	48.858372, 2.294481, 1); //paris
            //list = gcd.getFromLocation(40.65833044834671, -73.99418920683213, 1);
            list = gcd.getFromLocation(latitude, longitude, 1);
            String address = list.get(0).getAddressLine(0);
            //String message = list.get(0).toString();
            //if (list.size()>0){
            //findAddress();
            database.insertLocation(address,latitude, longitude);
            //else{
            //  Toast myToast = Toast.makeText(getActivity(),"Invalid Coordinates ",Toast.LENGTH_SHORT);
            //myToast.show();
            //}
            //SystemClock.sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}