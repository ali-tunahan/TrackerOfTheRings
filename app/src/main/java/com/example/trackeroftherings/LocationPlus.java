package com.example.trackeroftherings;

import android.location.Location;

public class LocationPlus extends Location {
    /**
     * An empty constructor needed by Firebase
     */
    public LocationPlus(){
        super("");
    }

    /**
     * The constructor that just passes the variable to super() class
     * @param provider
     */
    public LocationPlus(String provider) {
        super(provider);
    }

    /**
     * Copy constructor
     * @param l
     */
    public LocationPlus(Location l) {
        super(l);
    }

}
