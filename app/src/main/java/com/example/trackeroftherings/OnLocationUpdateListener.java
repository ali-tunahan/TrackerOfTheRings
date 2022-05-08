package com.example.trackeroftherings;

import android.location.Location;

public interface OnLocationUpdateListener {
    void onLocationChange(LocationPlus location);
    void onError(String error);
}