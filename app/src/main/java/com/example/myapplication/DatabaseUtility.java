package com.example.myapplication;

import android.location.Location;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtility {

    //References
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference accountsReference = database.getReference("Accounts");
    private static DatabaseReference vehiclesReference = database.getReference("Vehicles");
    private static DatabaseReference stopsReference = database.getReference("Stops");
    private static DatabaseReference routesReference = database.getReference("Routes");

    public static void add(Account anAccount){
        accountsReference.push().setValue(anAccount);
    }

    public static void add(Vehicle aVehicle){
        vehiclesReference.push().setValue(aVehicle);
    }

    public static void add(Stop aStop){
        stopsReference.push().setValue(aStop);
    }

    public static void add(Route aRoute){
        routesReference.push().setValue(aRoute);
    }

    //try using event listeners instead after getting this to work somehow
    public static Account read(Account anAccount){
        DatabaseReference databaseReference = accountsReference;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void once(@NonNull DataSnapshot snapshot) {
                if (snapshot != null){
                    mMap.clear();
                    int driverCount = (int) snapshot.getChildrenCount();
                    driverLocations = new Location[driverCount];
                    int i = 0;
                    for (DataSnapshot driverSnapshot : snapshot.getChildren()){
                        Location loc = null;
                        for (DataSnapshot l : driverSnapshot.getChildren()){
                            loc = l.getValue(Location.class);
                        }

                        driverLocations[i] = loc;
                        mMap.addMarker(new MarkerOptions().position(new LatLng(driverLocations[i].getLatitude(), driverLocations[i].getLongitude())).title(driverSnapshot.getKey()));
                        i++;
                    }


                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }
    }


}
