package com.example.trackeroftherings;

import java.util.List;

public class Demo {

    private List<String> list;
    private List<Stop> locationList;

    public Demo() {
    }

    public Demo(List<String> list, List<Stop> locationList) {
        this.list = list;
        this.locationList = locationList;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<Stop> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Stop> locationList) {
        this.locationList = locationList;
    }
}
