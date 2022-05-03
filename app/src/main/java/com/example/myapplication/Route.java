package com.example.myapplication;

import java.util.ArrayList;

public class Route 
{
    private String name;
    private ArrayList<Stop> stopsList;
    private ArrayList<Vehicle> activeVehicles;

    /**
     * Empty constructor
     */
    public Route(){}

    public Route(String name){
        this.name = name;
    }


    public ArrayList<Vehicle> getActiveVehicles() {
        return activeVehicles;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Stop> getStopsList() {
        return stopsList;
    }
    public void setStopsList(ArrayList<Stop> stopsList) {
        this.stopsList = stopsList;
    }
    public void setActiveVehicles(ArrayList<Vehicle> activeVehicles) {
        this.activeVehicles = activeVehicles;
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
