package com.example.trackeroftherings;

import android.location.Location;

public interface Locatable {

    public LocationPlus getLocation();

    public void setLocation(LocationPlus aLocation);
    
}
