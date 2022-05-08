package com.example.trackeroftherings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
     * Empty constructor
     */
    public Route(){
      this.stopsList = new ArrayList<Stop>();
      this.activeVehicles = new ArrayList<Vehicle>();
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public List<Stop> getStopsList() {
        return stopsList;
    }

    public void setStopsList(List<Stop> stopsList) {
        this.stopsList = stopsList;
    }

    public void setActiveVehicles(List<Vehicle> activeVehicles) {
        this.activeVehicles = activeVehicles;
    }

    public String getCompanyID(){
        return this.companyID;
    }



    public void addStop(int i, Stop aStop){
        stopsList.add(i, aStop);
        aStop.addRoute(this);
    }
    public void addStop(Stop aStop){ //adds the route to stops route list
        stopsList.add(aStop);
        aStop.addRoute(this);
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

    public void addActiveVehicle(Vehicle aVehicle){
        this.activeVehicles.add(aVehicle);
    }

    public void controlVehiclesActivity(){
        for (Vehicle aVehicle : activeVehicles) {
            if(!(aVehicle.isActive())){
                activeVehicles.remove(aVehicle);
            }
            
        }
    }

}
