package com.example.myapplication;

import android.location.Location;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.*;

public class Vehicle extends Account //implements Locatable
{
    
    private HashMap<Stop, String> history;
    private boolean isActive;
    private Route currentRoute;
    private Location location;

 
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
    
    /**
	 * Mutator for boolean isActive
	 * @param isActive
	 */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    /**
     * If the vehicle is near 20 meters of a stop method returns true using the haversine formula
     * @return boolean arrivedAtStop
     * https://www.movable-type.co.uk/scripts/latlong.html
     */
    public boolean arrivedAtStop(){

        double R = 6371000;
        double number1 = this.getLocation().getLatitude()*Math.PI/180;
        double number2 = this.getNextStop().getLocation().getLatitude()*Math.PI/180;
        double delta1 = (this.getNextStop().getLocation().getLatitude()-this.getLocation().getLatitude())*Math.PI/180;
        double delta2 = (this.getNextStop().getLocation().getLongitude()-this.getLocation().getLongitude())*Math.PI/180;
        double a= Math.sin(delta1/2)* Math.sin(delta1/2) + Math.cos(number1)*Math.cos(number2)*Math.sin(delta2/2)*Math.sin(delta2/2);
        double result = Math.atan2(Math.sqrt(a), Math.sqrt(1-a))*2*R;

        if( result < 20){
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
        if(this.arrivedAtStop()){
            Clock clock = Clock.systemDefaultZone();
            Instant instant = clock.instant();
            this.history.put(this.getNextStop(), instant.toString());
            
            Route newRoute = new Route();
            newRoute.setName(currentRoute.getName());
            ArrayList<Stop> copyStops = new ArrayList<Stop>();

            for(int i = 1; i < currentRoute.getStopsList().size();i++){ // i = 1 so that the current route does not involve passed stop
                copyStops.add(currentRoute.getStopsList().get(i));
            }
            this.setCurrentRoute(newRoute);
            

        }
    }

    /**
	 * İnitializes the copy of the chosen route to currentRoute
	 * @param aCurrentRoute
	 */
    public void setCurrentRoute(Route aCurrenRoute){
        Route copy = new Route();
        copy.setName(aCurrenRoute.getName());

        ArrayList<Stop> copyStops = new ArrayList<Stop>();

        for(int i = 0; i < aCurrenRoute.getStopsList().size();i++){
            copyStops.add(aCurrenRoute.getStopsList().get(i));
        }
        this.currentRoute = copy;
        if(!(aCurrenRoute.getActiveVehicles().contains(aCurrenRoute))){ //if the routes current vehicle ArrayList does not include this vehicle
            aCurrenRoute.addActiveVehicle(this);
        }
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
    public Location getLocation() {
        return this.location;
    }

    /**
     * Mutator for location
     * @param aLocation
     */
    public void setLocation(Location aLocation) {
        this.location = aLocation;
    }
    /**
     * Accessor for nextStop
     * @return nextStop
     */
    public Stop getNextStop(){
       return this.currentRoute.getStopsList().get(0);
    }

    /**
     * Accessor for vehicle history
     * @return history
     */
    public HashMap<Stop, String> getHistory(){
        return history;
    }



}
