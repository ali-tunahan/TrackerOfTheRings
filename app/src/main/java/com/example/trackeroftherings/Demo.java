package com.example.trackeroftherings;

import java.util.List;

public class Demo {

    private List<String> list;
    private List<LocationPlus> locationList;

    public Demo() {
    }

    public Demo(List<String> list, List<LocationPlus> locationList) {
        this.list = list;
        this.locationList = locationList;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<LocationPlus> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationPlus> locationList) {
        this.locationList = locationList;
    }
}
