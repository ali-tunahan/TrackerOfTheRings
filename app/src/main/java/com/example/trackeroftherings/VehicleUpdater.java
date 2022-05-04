package com.example.trackeroftherings;

public class VehicleUpdater {
    Vehicle vehicle;
    LocationHandler locationHandler;
    MainActivity mainActivity;
    DatabaseUtility databaseUtility;

    public VehicleUpdater(Vehicle aVehicle, MainActivity aMainActivity, DatabaseUtility aDatabaseUtility) {
        this.vehicle = aVehicle;
        this.mainActivity = aMainActivity;
        this.databaseUtility = aDatabaseUtility;
        this.locationHandler = new LocationHandler(mainActivity);
    }

    public void updateVehicle() {
        Vehicle updatedVehicle = new Vehicle(vehicle);
        locationHandler.updateGPS();
        updatedVehicle.setLocation(locationHandler.getmLastKnownLocation());
        databaseUtility.change(vehicle, updatedVehicle);
    }


}
