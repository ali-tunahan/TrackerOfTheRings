package com.example.trackeroftherings;

import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class Route implements Serializable
{
    private String name;
    private List<Stop> stopsList;
    private List<Vehicle> activeVehicles;
    private String companyID;

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    /**
     * Copy constructor
     * @param name
     * @param stopsList
     * @param activeVehicles
     * @param companyID
     */
    public Route(String name, List<Stop> stopsList, List<Vehicle> activeVehicles, String companyID) {
        this.name = name;
        this.stopsList = stopsList;
        this.activeVehicles = activeVehicles;
        this.companyID = companyID;
    }

    /**
     * Copy constructor
     */
    public Route(Route route) {
        if (route == null){
            this.name = null;
            this.stopsList = new ArrayList<Stop>();
            this.activeVehicles = new ArrayList<Vehicle>();
            this.companyID = null;
        }else{
            this.name = route.getName();
            this.stopsList = route.getStopsList();
            this.activeVehicles = new ArrayList<Vehicle>(route.getActiveVehicles());
            this.companyID = route.getCompanyID();
        }
    }

    /**
     * Empty constructor
     */
    public Route(){
        stopsList = new ArrayList<>();
        activeVehicles = new ArrayList<>();
        this.companyID = "";
        this.name = "";
    }

    /**
     * Standard constructor
     * @param name
     * @param companyID
     */
    public Route(String name, String companyID){
        this.name = name;
        this.stopsList = new ArrayList<Stop>();
        this.activeVehicles = new ArrayList<Vehicle>();
        this.companyID = companyID;
    }


    public List<Vehicle> getActiveVehicles() {
        return activeVehicles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name, boolean writeToDatabase) {
        if (writeToDatabase){
            DatabaseUtility.changeRouteName(this, name);
        }
        this.name = name;
    }

    public List<Stop> getStopsList() {
        return stopsList;
    }

    public void setStopsList(List<Stop> stopsList) {
        this.stopsList = stopsList;
        /*if (writeToDatabase){
            DatabaseUtility.changeStopsList(this,stopsList);
        }*/
    }

    public void setActiveVehicles(List<Vehicle> activeVehicles) {
        this.activeVehicles = activeVehicles;
    }

    public String getCompanyID(){
        return this.companyID;
    }

    public void addStop(int i, Stop aStop){
        //Adding the new route to the stop reference in the database
        Stop oldStop = aStop;
        List<Route> newRoutesList = new ArrayList<Route>(oldStop.getRoutesList());
        newRoutesList.add(this);
        Stop newStop = new Stop(aStop.getName(), aStop.getLocation(), newRoutesList,aStop.getCompanyID());
        DatabaseUtility.change(oldStop,newStop);
        stopsList.add(i, aStop);
    }
    public void addStop(Stop aStop){ //adds the route to stops route list
        //Adding the new route to the stop reference in the database
        Stop oldStop = aStop;
        List<Route> newRoutesList = new ArrayList<Route>(oldStop.getRoutesList());
        Route newRoute = new Route(this.name,this.companyID);
        newRoutesList.add(newRoute);
        /*
        String name = oldStop.getName();
        LocationPlus locationPlus = oldStop.getLocation();
        String companyID = oldStop.getCompanyID();
        Stop newStop = new Stop(name,locationPlus,companyID);*/
        oldStop.setRoutesList(newRoutesList);
        //update the database
        DatabaseUtility.change(new Stop(oldStop.getName(),oldStop.getLocation(),oldStop.getCompanyID()),oldStop);
        stopsList.add(aStop);
    }

    public Stop popStop(){
        Stop poppedStop = stopsList.get(stopsList.size()-1);
        stopsList.get(stopsList.size()-1).removeFromRoutesList(this);
        stopsList.remove(stopsList.size()-1);
        
        return poppedStop;
    }

    public Stop popStop(int i){
        Stop poppedStop = stopsList.get(i);
        stopsList.get(i).removeFromRoutesList(this);
        stopsList.remove(i);
        return poppedStop;
    }
    public Stop popStop(Stop aStop){
        Stop poppedstop = aStop;
        aStop.removeFromRoutesList(this);
        stopsList.remove(aStop);
        return poppedstop;
       
    }

    public void addActiveVehicle(Vehicle aVehicle, boolean writeToDatabase){
        if (writeToDatabase){
            DatabaseUtility.changeActiveVehicles(this,aVehicle);
        }
        this.activeVehicles.add(aVehicle);
    }

    public void removeActiveVehicle(Vehicle aVehicle, boolean writeToDatabase){
        if (writeToDatabase){
            DatabaseUtility.removeActiveVehicles(this,aVehicle);
        }
        this.activeVehicles.remove(aVehicle);
    }

    public void controlVehiclesActivity(){
        for (Vehicle aVehicle : activeVehicles) {
            if(!(aVehicle.isActive())){
                activeVehicles.remove(aVehicle);
            }
        }
    }

    public boolean equals(@NonNull Route aRoute){
        if(aRoute.getCompanyID().equals(this.getCompanyID())&& aRoute.getName().equals(this.getName())){
            return true;
        }
        else{
            return false;
        }
    }

}
