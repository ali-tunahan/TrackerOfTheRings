package com.example.trackeroftherings;

//TAKEN FROM https://stackoverflow.com/questions/36436686/how-to-get-current-location-using-seperate-class-in-android
//SOME TAKEN FROM https://www.youtube.com/watch?v=_xUcYfbtfsI&t=2184s&ab_channel=freeCodeCamp.org
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class LocationHandler {
    private MainActivity activity;
    public FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationPlus mLastKnownLocation;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    public OnLocationUpdateListener onLocationUpdateListener;
    private boolean updateStartedInternally = false;
    public GoogleMap mMap;
    public Context context;




    public LocationHandler(MainActivity activity , OnLocationUpdateListener onLocationUpdateListener) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.onLocationUpdateListener = onLocationUpdateListener;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        createLocationRequest();
        getDeviceLocation();

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                List<LocationPlus> locationPlusList = new ArrayList<LocationPlus>();
                //Create a LocationPlus ArrayList from Location ArrayList
                for (Location l : locationList){
                    locationPlusList.add(new LocationPlus(l));
                }
                if (locationPlusList.size() > 0) {
                    //The last location in the list is the newest
                    LocationPlus location = locationPlusList.get(locationPlusList.size() - 1);
                    mLastKnownLocation = location;
                    if (onLocationUpdateListener != null) {
                        onLocationUpdateListener.onLocationChange(location);
                        if (updateStartedInternally) {
                            stopLocationUpdate();
                        }
                    }
                }
            }
        };
    }

    private void getDeviceLocation() {
        /*
         * Retrieves the location of the local device using the FusedLocationClient, LocationRequest and LocationCallback
         */
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    mLastKnownLocation = new LocationPlus((Location)(task.getResult()));
                    if (mLastKnownLocation == null) {
                        updateStartedInternally = true;
                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    }

                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
            onLocationUpdateListener.onError(e.getMessage());

        }
    }

    public void startLocationUpdates() {
        /*
         * Called when the user gives permission for location tracking to start location updates
         */
        updateStartedInternally = false;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        updateGPS();
    }

    private void stopLocationUpdate() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);

    }


    //other new Methods but not using right now..
    protected void createLocationRequest() {
        /*
         * Used to refresh and re-request the user's location every 5000ms (5 seconds) with the highest accuracy possible
         */
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(5000);//set the interval in which you want to get locations
        mLocationRequest.setFastestInterval(5000);//if a location is available sooner you can get it (i.e. another app is using the location services)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public LocationPlus getmLastKnownLocation() {
        return mLastKnownLocation;
    }

    public void updateGPS() {
        /*
         * If user location can be retrieved, changes mLastKnownLocation to this new value
         */


        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // user gave permission
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    mLastKnownLocation = new LocationPlus(location);
                }
            });
        }
        else{
            // permission not given
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
        }

    }
}
