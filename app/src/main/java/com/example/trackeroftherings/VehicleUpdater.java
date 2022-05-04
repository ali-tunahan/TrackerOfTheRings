package com.example.trackeroftherings;

public class VehicleUpdater {
    Vehicle vehicle;
    LocationHandler locationHandler;
    MapsActivity mapsActivity;
    DatabaseUtility databaseUtility;

    public VehicleUpdater(Vehicle aVehicle, MainActivity aMapsActivity, DatabaseUtility aDatabaseUtility) {
        this.vehicle = aVehicle;
        this.mapsActivity = aMapsActivity;
        this.databaseUtility = aDatabaseUtility;
        this.locationHandler = new LocationHandler(mapsActivity);
    }

    public void updateVehicle() {
        Vehicle updatedVehicle = vehicle;
        locationHandler.updateGPS();
        updatedVehicle.setLocation(locationHandler.getmLastKnownLocation());
        databaseUtility.change(vehicle, updatedVehicle);
    }


}
