package com.example.trackeroftherings;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for login purposes for Vehicles
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
        List<Vehicle> vehicles = LocationController.getVehicles();
        Vehicle result = null;
        for(Vehicle v : vehicles){
            if (v.getPassword().equals(password) && v.getUsername().equals(username)){
                result = v;
            }
        }
        return result;
    }

    /**
     * Finds the correct Company object for the input credentials and returns it
     * If no such object exists returns null
     * @param username
     * @param password
     * @param companyID
     * @return Company object that credentials are correct for
     */
    public static Company companyLogin(String username, String password, String companyID){
        Company result = LocationController.getCompany();//Ideally will return a single Company
            //if (result.getPassword().equals(password) && result.getUsername().equals(username)){
                return result;
            //}
        //return null;
    }
}
