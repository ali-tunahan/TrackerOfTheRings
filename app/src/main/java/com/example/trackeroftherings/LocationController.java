package com.example.trackeroftherings;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the Vehicles and Stops the user should see
 * User should provide a companyID when creating the controller object
 * Constantly updates the locations of the objects in the ArrayList
 */
public class LocationController {

    private static List<Vehicle> vehicles;
    private static List<Stop> stops;
    private static List<Route> routes;
    private static Company company;
    private static String companyID;

    /**
     * Constructor that initializes ArrayLists and fills them
     * @param aCompanyID
     */
    public LocationController(String aCompanyID){
        companyID = aCompanyID;

        stops = new ArrayList<Stop>();
        readStops(aCompanyID, new FirebaseCallbackStop() {
            @Override
            public void onCallback(List<Stop> list) {
                stops = list;
            }
        });

        vehicles = new ArrayList<Vehicle>();
        readVehicles(aCompanyID, new FirebaseCallbackVehicles() {
            @Override
            public void onCallback(List<Vehicle> list) {
                vehicles = list;
            }
        });

        routes = new ArrayList<Route>();
        readRoutes(aCompanyID, new FirebaseCallbackRoute() {
            @Override
            public void onCallback(List<Route> list) {
                routes = list;
            }
        });

        company = new Company();
        readCompany(aCompanyID, new FirebaseCallbackCompany() {
            @Override
            public void onCallback(List<Company> companies) {
                if (companies.size() > 0){
                    company = companies.get(0);
                }
            }
        });

        updateVehicleLocations();
    }

    /**
     * Method to update the Vehicles ArrayList by reading changes on the database
     */
    public static void updateVehicleLocations(){
        DatabaseReference reference = DatabaseUtility.vehiclesReference;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot vehicleSnapshot : snapshot.getChildren()){
                    Vehicle v = vehicleSnapshot.getValue(Vehicle.class);
                    if (v.getCompanyID().equals(companyID)){
                        boolean contains = false;
                        for (int i = 0; i < vehicles.size(); i++) {
                            if (vehicles.get(i).equals(v)){
                                contains = true;
                            }
                        }
                        if (contains){
                            //removing the object if old version exists
                            int index = 0;
                            for (int i = 0; i < vehicles.size(); i++) {
                                if (vehicles.get(i).equals(v)){
                                    index = i;
                                }
                            }
                            vehicles.remove(index);


                        }
                        //adding the new object
                        boolean contains2 = false;
                        for (int i = 0; i < vehicles.size(); i++) {
                            if (vehicles.get(i).equals(v)){
                                contains2 = true;
                            }
                        }
                        if (!contains2){
                            vehicles.add(v);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Method to update all objects other than the vehicles
     */
    public static void updateObjects(){
        readStops(LocationController.getCompanyID(), new FirebaseCallbackStop() {
            @Override
            public void onCallback(List<Stop> list) {
                stops = list;
            }
        });

        readVehicles(LocationController.getCompanyID(), new FirebaseCallbackVehicles() {
            @Override
            public void onCallback(List<Vehicle> list) {
                vehicles = list;
            }
        });

        readRoutes(LocationController.getCompanyID(), new FirebaseCallbackRoute() {
            @Override
            public void onCallback(List<Route> list) {
                routes = list;
            }
        });

        readCompany(LocationController.getCompanyID(), new FirebaseCallbackCompany() {
            @Override
            public void onCallback(List<Company> companies) {
                if (companies.size() > 0){
                    company = companies.get(0);
                }
            }
        });
    }

    /**
     * Method to read stops from the database
     * @param companyID
     * @param firebaseCallback
     */
    private static void readStops(String companyID, FirebaseCallbackStop firebaseCallback){
        DatabaseReference reference = DatabaseUtility.stopsReference;
        ArrayList<Stop> matchingStops = new ArrayList<Stop>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Stop currentStop = data.getValue(Stop.class);
                    if (currentStop.getCompanyID().equals(companyID)){
                        matchingStops.add(currentStop);
                    }
                }
                //When reading is finished, fires this object to pass the ArrayList
                firebaseCallback.onCallback(matchingStops);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     * Method to read vehicles from the database
     * @param companyID
     * @param firebaseCallback
     */
    private static void readVehicles(String companyID, FirebaseCallbackVehicles firebaseCallback){
        DatabaseReference reference = DatabaseUtility.vehiclesReference;
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
                //When reading is finished, fires this object to pass the ArrayList
                firebaseCallback.onCallback(matchingVehicles);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Method to read routes from the database
     * @param companyID
     * @param firebaseCallback
     */
    private static void readRoutes(String companyID, FirebaseCallbackRoute firebaseCallback){
        DatabaseReference reference = DatabaseUtility.routesReference;
        ArrayList<Route> matchingRoutes = new ArrayList<Route>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Route currentRoute = data.getValue(Route.class);
                    if (currentRoute.getCompanyID().equals(companyID)){
                        matchingRoutes.add(currentRoute);
                    }
                }
                //When reading is finished, fires this object to pass the ArrayList
                firebaseCallback.onCallback(matchingRoutes);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Method to read companies from the database (should be a single company in reality)
     * @param companyID
     * @param firebaseCallback
     */
    private static void readCompany(String companyID, FirebaseCallbackCompany firebaseCallback){
        DatabaseReference reference = DatabaseUtility.companiesReference;
        ArrayList<Company> matchingCompany = new ArrayList<Company>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Company currentCompany = data.getValue(Company.class);
                    if (currentCompany.getCompanyID().equals(companyID)){
                        matchingCompany.add(currentCompany);
                    }
                }
                //When reading is finished, fires this object to pass the ArrayList
                firebaseCallback.onCallback(matchingCompany);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Method to add a Stop to the stops ArrayList
     * @param aStop
     */
    public static void addStop(Stop aStop){
        stops.add(aStop);
    }

    /**
     * Method to add a Route to the routes ArrayList
     * @param aRoute
     */
    public static void addRoute(Route aRoute){
        routes.add(aRoute);
    }

    /**
     * Method to add a Vehicle to vehicles ArrayList
     * @param aVehicle
     */
    public static void addVehicle(Vehicle aVehicle){
        boolean contains = false;
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).equals(aVehicle)){
                contains = true;
            }
        }
        if (!contains){
            vehicles.add(aVehicle);
        }
    }

    //Used for waiting for callback
    //Source: https://www.youtube.com/watch?v=hnDkA0V1bU8
    private interface FirebaseCallbackRoute{
        void onCallback(List<Route> list);
    }
    private interface FirebaseCallbackVehicles{
        void onCallback(List<Vehicle> list);
    }
    private interface FirebaseCallbackStop{
        void onCallback(List<Stop> list);
    }
    private interface FirebaseCallbackCompany{
        void onCallback(List<Company> companies);
    }

    //Getter Setters
    public static List<Vehicle> getVehicles() {
        return vehicles;
    }

    public static void setVehicles(List<Vehicle> vehicles) {
        LocationController.vehicles = vehicles;
    }

    public static List<Stop> getStops() {
        return stops;
    }

    public static void setStops(List<Stop> stops) {
        LocationController.stops = stops;
    }

    public static List<Route> getRoutes() {
        return routes;
    }

    public static void setRoutes(List<Route> routes) {
        LocationController.routes = routes;
    }

    public static Company getCompany() {
        return company;
    }

    public static void setCompany(Company company) {
        LocationController.company = company;
    }

    public static String getCompanyID() {
        return companyID;
    }

    public static void setCompanyID(String companyID) {
        LocationController.companyID = companyID;
    }

}
