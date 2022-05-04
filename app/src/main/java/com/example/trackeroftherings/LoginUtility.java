package com.example.trackeroftherings;

import java.util.ArrayList;

/**
 * Class for login purposes vor Vehicles
 */
public class LoginUtility {
    /**
     * Finds the correct Vehicle object for the input credentials and returns it
     * If no such object exists returns null
     * @param username
     * @param password
     * @param companyID
     * @return Vehicle object that credentials are correct for
     */
    public static Vehicle vehicleLogin(String username, String password, String companyID){
        ArrayList<Vehicle> vehicles = DatabaseUtility.readVehicles(companyID);
        Vehicle result = null;
        for(Vehicle v : vehicles){
            if (v.getPassword().equals(password) && v.getUsername().equals(username)){
                result = v;
            }
        }
        return result;
    }
}
