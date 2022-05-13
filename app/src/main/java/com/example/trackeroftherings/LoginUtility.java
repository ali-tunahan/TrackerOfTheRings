package com.example.trackeroftherings;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for login purposes for Vehicles
 */
public class LoginUtility {
    private static List<Company> company;
    private static List<Vehicle> vehicles;

    /**
     * A constructor that reads all Accounts from the database
     */
    public LoginUtility(){
        vehicles = new ArrayList<Vehicle>();
        readVehicles(new FirebaseCallbackVehicles() {
            @Override
            public void onCallback(List<Vehicle> list) {
                vehicles = list;
            }
        });

        company = new ArrayList<Company>();
        readCompany(new FirebaseCallbackCompany() {
            @Override
            public void onCallback(List<Company> companies) {
                company = companies;
            }
        });
    }
    
    /**
     * Finds the correct Vehicle object for the input credentials and returns it
     * If no such object exists returns null
     * @param username
     * @param password
     * @param companyID
     * @return Vehicle object that credentials are correct for
     */
    public static Vehicle vehicleLogin(String username, String password, String companyID){
        if (vehicles.size() == 0){
            return null;
        }
        ArrayList<Vehicle> v = new ArrayList<Vehicle>();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getCompanyID().equals(companyID)){
                v.add(vehicles.get(i));
            }
        }
        if (v.size() == 0){
            return null;
        }
        for (int i = 0; i < v.size(); i++) {
            Vehicle currentVehicle = v.get(i);
            if (currentVehicle.getPassword().equals(password) && currentVehicle.getUsername().equals(username)){
                return currentVehicle;
            }
        }
        return null;
    }

    /**
     * Finds the correct Company object for the input credentials and returns it
     * If no such object exists returns null
     * @param username
     * @param password
     * @param companyID
     * @return Company object that credentials are correct for
     * if it does not exist returns null
     */
    public static Company companyLogin(String username, String password, String companyID){
        if (company.size() == 0){
            return null;
        }
        Company c = null;
        for (int i = 0; i < company.size(); i++) {
            if (company.get(i).getCompanyID().equals(companyID)){
                c = company.get(i);
            }
        }
        if (c == null){
            return null;
        }
        if (c.getPassword().equals(password) && c.getUsername().equals(username)){
                return c;
        }
        return null;
    }

    /**
     * Method to read all vehicles from the database
     * @param firebaseCallback
     */
    private void readVehicles(FirebaseCallbackVehicles firebaseCallback){
        DatabaseReference reference = DatabaseUtility.vehiclesReference;
        ArrayList<Vehicle> matchingVehicles = new ArrayList<Vehicle>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Vehicle currentVehicle = data.getValue(Vehicle.class);
                    matchingVehicles.add(currentVehicle);
                }

                firebaseCallback.onCallback(matchingVehicles);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /**
     * Method to read all companies from the database
     * @param firebaseCallback
     */
    private void readCompany(FirebaseCallbackCompany firebaseCallback){
        DatabaseReference reference = DatabaseUtility.companiesReference;
        ArrayList<Company> matchingCompany = new ArrayList<Company>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Company currentCompany = data.getValue(Company.class);
                        matchingCompany.add(currentCompany);
                }
                firebaseCallback.onCallback(matchingCompany);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    
    //callback interfaces
    //source: https://www.youtube.com/watch?v=hnDkA0V1bU8
    private interface FirebaseCallbackVehicles{
        void onCallback(List<Vehicle> list);
    }
    
    private interface FirebaseCallbackCompany{
        void onCallback(List<Company> companies);
    }
}
