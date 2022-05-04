package com.example.trackeroftherings;

//TAKEN FROM https://stackoverflow.com/questions/36436686/how-to-get-current-location-using-seperate-class-in-android
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

import java.util.List;

public class LocationHandler {
    private MainActivity activity;
    public FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
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
        //mMap = getmMap();
        createLocationRequest();
        getDeviceLocation();

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    //The last location in the list is the newest
                    Location location = locationList.get(locationList.size() - 1);
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
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    mLastKnownLocation = (Location) task.getResult();
                    if (mLastKnownLocation == null) {
                        updateStartedInternally = true;
                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    } else {
                        // onLocationUpdateListener.onLocationChange(mLastKnownLocation);
                    }
                } else {
                    //onLocationUpdateListener.onError("Can't get Location");
                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
            //onLocationUpdateListener.onError(e.getMessage());

        }
    }

    public void startLocationUpdates() {
        /*if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }*/
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
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(5000);//set the interval in which you want to get locations
        mLocationRequest.setFastestInterval(5000);//if a location is available sooner you can get it (i.e. another app is using the location services)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public Location getmLastKnownLocation() {
        return mLastKnownLocation;
    }

    public void updateGPS() {
        // get permissions from the user to track GPS
        // get current location from fused clients
        // update UI - i.e. set all properties in their associated TextView elements


        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // user gave permission
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //mMap = activity.mMap;
                    mLastKnownLocation = location;
                    //loco.trye(MapsActivity.this);
                    //mMap.clear();
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())).title("Long: " + mLastKnownLocation.getLongitude() + ", Lat: " +mLastKnownLocation.getLatitude()));
                    //binding.textView.setText("Lat " +handler.getmLastKnownLocation().getLatitude() + " , Long " + handler.getmLastKnownLocation().getLongitude());
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
