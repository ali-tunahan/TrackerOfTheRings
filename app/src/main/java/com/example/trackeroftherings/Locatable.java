package com.example.trackeroftherings;

import android.location.Location;

/**
 * An interface that assures the object that implement this class have location and proper get functions
 */
public interface Locatable {

    public LocationPlus getLocation();

    public void setLocation(LocationPlus aLocation);
    
}
