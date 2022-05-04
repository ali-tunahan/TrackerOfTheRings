package com.example.trackeroftherings;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Stores the Vehicles and Stops the user should see
 * User should provide a companyID when creating the controller object
 * Constantly updates the locations of the objects in the ArrayList
 */
public class LocationController {

    private ArrayList<Vehicle> vehicles;
    private ArrayList<Stop> stops;
    private String companyID;

    /**
     * Constructor that initializes ArrayLists and fills them
     * @param aCompanyID
     */
    public LocationController(String aCompanyID){
        companyID = aCompanyID;
        vehicles = new ArrayList<Vehicle>();
        stops = new ArrayList<Stop>();
        this.stops = DatabaseUtility.readStops(this.companyID);
        updateVehicleLocations();
    }

    private void updateVehicleLocations(){
        DatabaseReference reference = DatabaseUtility.vehiclesReference;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot vehicleSnapshot : snapshot.getChildren()){
                    Vehicle v = vehicleSnapshot.getValue(Vehicle.class);
                    if (v.getCompanyID().equals(companyID)){
                        if (vehicles.contains(v)){
                            //removing the object if old version exists
                            vehicles.remove(v);
                        }
                        //adding the new object
                        vehicles.add(v);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
