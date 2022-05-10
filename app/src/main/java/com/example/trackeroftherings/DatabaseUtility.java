package com.example.trackeroftherings;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DatabaseUtility {

    //References
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference companiesReference = database.getReference("Companies");
    public static DatabaseReference vehiclesReference = database.getReference("Vehicles");
    public static DatabaseReference stopsReference = database.getReference("Stops");
    public static DatabaseReference routesReference = database.getReference("Routes");

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
                    if (data.getValue(Company.class).equals(oldCompany)){
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
                    Stop currentStop = data.getValue(Stop.class);
                    if (currentStop.equals(oldStop)){
                        String key = data.getKey();
                        reference.child(key).removeValue();
                        reference.child(key).setValue(newStop);
                        //reference.child(key).setValue(newStop);
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
                    if (data.getValue(Route.class).equals(oldRoute)){
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

    public static void addRouteToStop(Stop oldStop, Stop newStop){
        DatabaseReference reference = stopsReference;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Stop currentStop = data.getValue(Stop.class);
                    if (currentStop.equals(oldStop)){
                        String key = data.getKey();
                        List<Route> existingRoutes = currentStop.getRoutesList();
                        for(Route currentRoute : existingRoutes){
                            newStop.addRoute(currentRoute);
                        }

                        reference.child(key).setValue(newStop);
                        //reference.child(key).setValue(newStop);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Change the name of a Stop in the Database
     * @param oldStop
     * @param newName
     */
    public static void changeStopName(Stop oldStop, String newName){
        Stop newStop = new Stop(oldStop);
        newStop.setName(newName,false);
        Stop oldStopCopy = new Stop(oldStop);
        change(oldStopCopy,newStop);
    }

    /**
     * Change the name of a Route in the Database
     * @param oldRoute
     * @param newName
     */
    public static void changeRouteName(Route oldRoute, String newName){
        Route newRoute = new Route(oldRoute);
        newRoute.setName(newName,false);
        Route oldRouteCopy = new Route(oldRoute);
        change(oldRouteCopy,newRoute);
    }

    /**
     * Change the stops list of Route in database
     * @param oldRoute
     * @param stops
     */
    public static void changeStopsList(Route oldRoute, List<Stop> stops){
        Route newRoute = new Route(oldRoute);
        newRoute.setStopsList(stops);
        change(oldRoute,newRoute);
    }

    public static void changeStopsLocation(Stop oldStop, LocationPlus locationPlus){
        Stop newStop = new Stop(oldStop);
        newStop.setLocation(locationPlus);
        Stop oldStopCopy = new Stop(oldStop);
        change(oldStopCopy,newStop);
    }

    public static void changeActiveVehicles(Route oldRoute, Vehicle vehicle){
        Route newRoute = new Route(oldRoute);
        newRoute.addActiveVehicle(vehicle,false);
        Route oldRouteCopy = new Route(oldRoute);
        change(oldRouteCopy,newRoute);
    }

    public static void removeActiveVehicles(Route oldRoute, Vehicle vehicle){
        Route newRoute = new Route(oldRoute);
        newRoute.removeActiveVehicle(vehicle,false);
        Route oldRouteCopy = new Route(oldRoute);
        change(oldRouteCopy,newRoute);
    }

    public static void setVehicleActivity(Vehicle oldVehicle, boolean isActive){
        Vehicle newVehicle = new Vehicle(oldVehicle);
        newVehicle.setActive(isActive,false);
        Vehicle oldVehicleCopy = new Vehicle(oldVehicle);
        change(oldVehicleCopy,newVehicle);
    }

    public static void changeCurrentRoute(Vehicle oldVehicle, Route newRoute){
        Vehicle newVehicle = new Vehicle(oldVehicle);
        newVehicle.setCurrentRoute(newRoute,false);
        newVehicle.setActive(true,false);
        Vehicle oldVehicleCopy = new Vehicle(oldVehicle);
        change(oldVehicleCopy,newVehicle);
    }

    public static void changeVehicleInfo(Vehicle oldVehicle, String name, String password){

        Vehicle newVehicle = new Vehicle(oldVehicle);
        newVehicle.setUsername(name);
        newVehicle.setPassword(password);
        Vehicle oldVehicleCopy = new Vehicle(oldVehicle);
        change(oldVehicleCopy,newVehicle);
    }





}
