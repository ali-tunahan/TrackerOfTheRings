package com.example.trackeroftherings;

public class VehicleUpdater {
    Vehicle vehicle;
    LocationHandler locationHandler;
    MainActivity mainActivity;

    public VehicleUpdater(Vehicle aVehicle, MainActivity aMainActivity) {
        this.vehicle = aVehicle;
        this.mainActivity = aMainActivity;

        this.locationHandler = new LocationHandler(mainActivity, MapsFragment.onLocationUpdateListener);
    }

    public void updateVehicle() {
        Vehicle updatedVehicle = new Vehicle(vehicle);
        locationHandler.updateGPS();
        updatedVehicle.setLocation(locationHandler.getmLastKnownLocation());
        DatabaseUtility.change(vehicle, updatedVehicle);
    }


}
