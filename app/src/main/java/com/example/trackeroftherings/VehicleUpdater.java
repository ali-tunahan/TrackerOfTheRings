package com.example.trackeroftherings;

public class VehicleUpdater {
    Vehicle vehicle;
    LocationHandler locationHandler;
    MainActivity mainActivity;
    /*
     * A vehicleUpdater object is used by every vehicle registered to the database tp update their locations on the database in a periodic fashion
     */
    public VehicleUpdater(Vehicle aVehicle, MainActivity aMainActivity) {
        this.vehicle = aVehicle;
        this.mainActivity = aMainActivity;

        this.locationHandler = new LocationHandler(mainActivity, MapsFragment.onLocationUpdateListener);
    }
    /*
     * Uses the copy constructor of Vehicle to make a copy of the current vehicle object, than assigns this copy the new location and replaces the old vehicle with the new vehicle within the databse
     */
    public void updateVehicle() {
        Vehicle updatedVehicle = new Vehicle(vehicle);
        locationHandler.updateGPS();
        updatedVehicle.setLocation(locationHandler.getmLastKnownLocation());
        updatedVehicle.addToHistory();
        DatabaseUtility.change(vehicle, updatedVehicle);
    }

}
