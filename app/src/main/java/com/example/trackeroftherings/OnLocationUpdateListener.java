package com.example.trackeroftherings;

import android.location.Location;
//TAKEN FROM https://stackoverflow.com/questions/36436686/how-to-get-current-location-using-seperate-class-in-android
public interface OnLocationUpdateListener {
    void onLocationChange(LocationPlus location);
    void onError(String error);
}