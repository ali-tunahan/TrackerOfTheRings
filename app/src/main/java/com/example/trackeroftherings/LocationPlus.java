package com.example.trackeroftherings;

import android.location.Location;

public class LocationPlus extends Location {
    public LocationPlus(){
        super("");
    }

    public LocationPlus(String provider) {
        super(provider);
    }

    public LocationPlus(Location l) {
        super(l);
    }

    public LocationPlus(int lat, int lng)
    {
        super("");
        this.setLatitude (lat);
        this.setLongitude (lng);
    }

}
