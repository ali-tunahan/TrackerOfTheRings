package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DatabaseUtility {

    //References
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference companiesReference = database.getReference("Companies");
    private static DatabaseReference vehiclesReference = database.getReference("Vehicles");
    private static DatabaseReference stopsReference = database.getReference("Stops");
    private static DatabaseReference routesReference = database.getReference("Routes");

    /**
     * Add method for Companies
     * @param aCompany
     * adds aCompany to database
     */
    public static void add(Company aCompany){
        companiesReference.push().setValue(aCompany);
    }

    /**
     * Add method for Vehicles
     * @param aVehicle
     * adds aVehicle to database
     */
    public static void add(Vehicle aVehicle){
        vehiclesReference.push().setValue(aVehicle);
    }

    /**
     * Add method for Stops
     * @param aStop
     * adds aStop to database
     */
    public static void add(Stop aStop){
        stopsReference.push().setValue(aStop);
    }

    /**
     * Add method for Routes
     * @param aRoute
     * adds aRoute to database
     */
    public static void add(Route aRoute){
        routesReference.push().setValue(aRoute);
    }

    /**
     * change the object in database
     * @param oldCompany
     * @param newCompany
     * change the oldCompany with newCompany
     */
    public static void change(Company oldCompany, Company newCompany){
        DatabaseReference reference = companiesReference;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    if (data.getValue(Vehicle.class).equals(oldCompany)){
                        String key = data.getKey();
                        reference.child(key).setValue(newCompany);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**
     * change the object in database
     * @param oldVehicle
     * @param newVehicle
     * change the oldVehicle with newVehicle
     */
    public static void change(Vehicle oldVehicle, Vehicle newVehicle){
        DatabaseReference reference = vehiclesReference;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    if (data.getValue(Vehicle.class).equals(oldVehicle)){
                        String key = data.getKey();
                        reference.child(key).setValue(newVehicle);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * change the object in database
     * @param oldStop
     * @param newStop
     * change the oldStop with newStop
     */
    public static void change(Stop oldStop, Stop newStop){
        DatabaseReference reference = stopsReference;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    if (data.getValue(Account.class).equals(oldStop)){
                        String key = data.getKey();
                        reference.child(key).setValue(newStop);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * change the object in database
     * @param oldRoute
     * @param newRoute
     * change the oldRoute with newRoute
     */
    public static void change(Route oldRoute, Route newRoute){
        DatabaseReference reference = routesReference;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    if (data.getValue(Account.class).equals(oldRoute)){
                        String key = data.getKey();
                        reference.child(key).setValue(newRoute);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Returns an ArrayList of Company objects matching with the input companyID
     * Ideally only a single Company should match
     * @param companyID the ID to search for
     * @return ArrayList<Company>
     */
    public static ArrayList<Company> readCompanies(String companyID){
        DatabaseReference reference = routesReference;
        ArrayList<Company> matchingCompanies = new ArrayList<Company>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Company currentCompany = data.getValue(Company.class);
                    if (currentCompany.getCompanyID().equals(companyID)){
                        matchingCompanies.add(currentCompany);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return matchingCompanies;
    }

    /**
     * Returns Vehicles that belong to the input companyID
     * @param companyID the ID of company that owns the vehicles
     * @return ArrayList<Vehicle>
     */
    public static ArrayList<Vehicle> readVehicles(String companyID){
        DatabaseReference reference = vehiclesReference;
        ArrayList<Vehicle> matchingVehicles = new ArrayList<Vehicle>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Vehicle currentVehicle = data.getValue(Vehicle.class);
                    if (currentVehicle.getCompanyID().equals(companyID)){
                        matchingVehicles.add(currentVehicle);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return matchingVehicles;
    }

    /**
     * Returns all stops in the database
     * @return ArrayList<Stop>
     */
    public static ArrayList<Stop> readStops(){
        DatabaseReference reference = stopsReference;
        ArrayList<Stop> allStops = new ArrayList<Stop>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Company currentStop = data.getValue(Stop.class);
                    allStops.add(currentStop);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return allStops;
    }

    /**
     * Returns all routes in the database
     * @return ArrayList<Route>
     */
    public static ArrayList<Route> readRoutes(){
        DatabaseReference reference = routesReference;
        ArrayList<Route> allRoutes = new ArrayList<Route>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Route currentRoute = data.getValue(Route.class);
                    allRoutes.add(currentRoute);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return allRoutes;
    }

}
