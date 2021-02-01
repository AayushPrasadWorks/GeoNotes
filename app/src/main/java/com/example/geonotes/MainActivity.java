package com.example.geonotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements LocationListener {

    //private String provider;
    private LocationManager locationManager;
    private String lat = "";
    private String lon = "";
    private String locationSent = "";
    private static final String TAG = "Output";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText view = findViewById(R.id.textView);
                String message = view.getText().toString();
                checkPermission();
                if (checkPermission() == 1) {
                    locationSent = "";
                }
                Log.d(TAG, message);
                if (!message.isEmpty()) {
                    SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
                    String currDate = date.format(new Date());
                    SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                    String currTime = time.format(new Date());


                    Log.d(TAG, "Message: " + message);
                    Log.d(TAG, "Date: " + currDate);
                    Log.d(TAG, "Time: " + currTime);
                    Log.d(TAG, "Location: " + locationSent);
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    TextNote note = new TextNote(message, currDate, currTime, locationSent);
                    mDatabase.child("Text-Notes").child(UUID.randomUUID().toString()).setValue(note);


                    //System.out.println("Location: "+locationSent);
                }
                view.setText("");
            }
        });
    }

    public int checkPermission() {
        Criteria criteria = new Criteria();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return 1;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        String provider;
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        provider = locationManager.getBestProvider(criteria, false);
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
            System.out.println("Hello");
        }
        //Changes


        return 0;
    }



    @Override
    public void onLocationChanged(Location location) {
        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());
        locationSent = lat+" "+lon;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}