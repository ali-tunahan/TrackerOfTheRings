package com.example.trackeroftherings;

import android.location.Location;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.*;
import java.util.List;
import java.util.Map;

public class Vehicle extends Account implements Locatable, Serializable
{

    private Map<String, String> history;
    private boolean isActive;
    private Route currentRoute;


    private LocationPlus location;
    private Company company;

 
    /**
     * Constructor 
     * @param aName
     * @param aPassword
     * @param aCompanyID
     */
    public Vehicle(String aName, String aPassword, String aCompanyID){
        this.setUsername(aName);
        this.setPassword(aPassword);
        this.setCompanyID(aCompanyID);
        this.setCompany(authenticateCompanyID(aCompanyID));
    }

/**
 * Empty constructor
 */
    public Vehicle(){}

    /**
	 * Accessor method for boolean isActive
	 * @return isActive
	 */
    public boolean isActive() {
        return isActive;
    }

    public boolean getIsActive(){
        return isActive;
    }

    /**
     * Copy constructor
     * @param aVehicle
     */
    public Vehicle(Vehicle aVehicle){
        this.setUsername(aVehicle.getUsername());
        this.setPassword(aVehicle.getPassword());
        this.setCompanyID(aVehicle.getCompanyID());
        this.setCompany(authenticateCompanyID(aVehicle.getCompanyID()));
        this.setCurrentRoute(new Route(aVehicle.getCurrentRoute()),false);
        this.isActive = aVehicle.isActive;
        this.history = new HashMap<String, String>();
    }  
    
    /**
	 * Mutator for boolean isActive
	 * @param isActive
	 */
    public void setActive(boolean isActive, boolean writeToDatabase) {
        if (writeToDatabase){
            DatabaseUtility.setVehicleActivity(this,isActive);
        }
        this.isActive = isActive;
    }

    public void setHistory(Map<String, String> history) {
        this.history = history;
    }

    /**
     * If the vehicle is near x meters of a stop method returns true using the haversine formula
     * @return boolean arrivedAtStop
     * https://www.movable-type.co.uk/scripts/latlong.html
     */
    public boolean arrivedAtStop(Stop stop){

        if (stop == null){
            return false;
        }
        int targetDistanceInMeters = 50;//x
        double R = 6371000;
        double number1 = this.getLocation().getLatitude()*Math.PI/180;
        double number2 = stop.getLocation().getLatitude()*Math.PI/180;
        double delta1 = (stop.getLocation().getLatitude()-this.getLocation().getLatitude())*Math.PI/180;
        double delta2 = (stop.getLocation().getLongitude()-this.getLocation().getLongitude())*Math.PI/180;
        double a= Math.sin(delta1/2)* Math.sin(delta1/2) + Math.cos(number1)*Math.cos(number2)*Math.sin(delta2/2)*Math.sin(delta2/2);
        double result = Math.atan2(Math.sqrt(a), Math.sqrt(1-a))*2*R;

        if( result < targetDistanceInMeters){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Adds the time to history when the vehicle passes a stop and updates the currentRoute
     */
    public void addToHistory(){
        if (this.getCurrentRoute()==null){
            return;
        }
        if (this.getCurrentRoute().getName() == null){
            return;
        }
        for (int i = 0; i < LocationController.getRoutes().size(); i++){
            if(this.getCurrentRoute().getName().equals(LocationController.getRoutes().get(i).getName()))
                this.currentRoute = LocationController.getRoutes().get(i);
        }

        if(!(this.getCurrentRoute().getStopsList() == null)) {
            for (int i = 0; i < this.getCurrentRoute().getStopsList().size(); i++) {

                if (this.arrivedAtStop(this.getCurrentRoute().getStopsList().get(i))) {
                    Clock clock = Clock.systemUTC();
                    Instant instant = clock.instant();
                    this.history.put(this.getCurrentRoute().getStopsList().get(i).toString(), instant.toString());

                    }
            }
        }


    }

    /**
	 * Initializes the copy of the chosen route to currentRoute
	 * @param aCurrentRoute
	 */
    public void setCurrentRoute(Route aCurrentRoute, boolean writeToDatabase){

        if (aCurrentRoute == null){
            this.currentRoute = null;
            return;
        }

        if (writeToDatabase){
            DriverCompanyLoginFragment.getSelfVehicle().setActive(true,false);
        }
        Route copy = new Route();
        copy.setName(aCurrentRoute.getName(),false);

        List<Stop> copyStops = new ArrayList<Stop>();

        for(int i = 0; i < aCurrentRoute.getStopsList().size();i++){
            copyStops.add(aCurrentRoute.getStopsList().get(i));
        }

        this.currentRoute = copy;
    }


    public void changeInfo(String name, String password){

        DatabaseUtility.changeVehicleInfo(this, name, password);

        this.setUsername(name);
        this.setPassword(password);

    }

    /**
	 * Accessor method for currentRoute
	 * @return currentRoute
	 */
    public Route getCurrentRoute(){
        return this.currentRoute;
    }

    /**
     * Accessor method for location
     * @return location
     */
    public LocationPlus getLocation() {
        return location;
    }

    /**
     * Mutator for location
     * @param aLocation
     */
    public void setLocation(LocationPlus aLocation) {
        this.location = aLocation;
    }
    /**
     * Accessor for nextStop
     * @return nextStop
     */
    public Stop getNextStop(){
        if (this.currentRoute == null){
            return null;
        }
        List<Stop> stops = this.currentRoute.getStopsList();
        if (stops == null){
            return null;
        }
        if (stops.size() == 0){
            return null;
        }
       return this.currentRoute.getStopsList().get(0);
    }

    /**
     * Accessor for vehicle history
     * @return history
     */
    public Map<String, String> getHistory(){
        return history;
    }

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }


    public boolean equals(@NonNull Vehicle aVehicle){
        if(aVehicle.getCompanyID().equals(this.getCompanyID())&& aVehicle.getPassword().equals(this.getPassword()) && aVehicle.getUsername().equals(this.getUsername())){
            return true;
        }
        else{
            return false;
        }

    }


    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

    public String historyToString(){
        if(this.history == null){
            return "No Activiy";
        }
        else{
            return history.toString();
        }
    }
}
