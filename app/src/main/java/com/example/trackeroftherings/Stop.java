package com.example.trackeroftherings;

import android.location.Location;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stop implements Locatable, Serializable
{
	private String name;
	private LocationPlus location;
	private List<Route> routesList;
	private String companyID;

	/**
	 * Copy constructor
	 * @param name
	 * @param location
	 * @param routesList
	 * @param companyID
	 */
	public Stop(String name, LocationPlus location, List<Route> routesList, String companyID) {
		this.name = name;
		this.location = location;
		this.routesList = routesList;
		this.companyID = companyID;
	}

	/**
	 * Copy constructor
	 */
	public Stop(Stop s) {
		this.name = s.getName();
		this.location = s.getLocation();
		this.routesList = s.getRoutesList();
		this.companyID = s.getCompanyID();
	}

	/**
	 * Empty constructor
	 */
	public Stop(){
		routesList = new ArrayList<>();
	}

	/**
	 * Constructor
	 * No stop will be created with a route
	 * @param aName
	 * @param aLocation
	 */
	public Stop (String aName, LocationPlus aLocation, String aCompanyID)
	{
		this.name = aName;
		this.location = aLocation;
		this.routesList = new ArrayList <Route> ();
		this.companyID = aCompanyID;
	}

	/**
	 * Mutator for name
	 * @param aName
	 */
	public void setName (String aName, boolean writeToDatabase)
	{
		if (writeToDatabase){
			DatabaseUtility.changeStopName(this, aName);
		}
		this.name = aName;
	}

	/**
	 * Mutator for location
	 * @param aLocation
	 */
	public void setLocationDatabase (LocationPlus aLocation)
	{
		DatabaseUtility.changeStopsLocation(this, aLocation);
		this.location = aLocation;

	}

	public void setLocation(LocationPlus location){
		this.location = location;
	}
	/**
	 * Adds aRoute to the end of routesList
	 * @param aRoute
	 */
	public void addRoute (Route aRoute)
	{
		this.routesList.add(aRoute);
	}

	/**
	 * Removes aRoute form routesList
	 * @param aRoute
	 */
	public void removeRoute (Route aRoute)
	{
		this.routesList.remove (aRoute);
	}

	/**
	 * Accessor method for name
	 * @return name
	 */
	public String getName ()
	{
		return this.name;
	}

	/**
	 * Accessor method for location
	 * @return location
	 */
	public LocationPlus getLocation()
	{
		return this.location;
	}

	/**
	 * Accessor method for routesList
	 * @return ArrayList<Route> routesList
	 */

	/**
	 * Accessor method for companyID
	 * @return String companyID
	 */
	public String getCompanyID(){return this.companyID;}

	/**
	 * Adds a route to the Routes list of the Stops that hold Routes this Stop exists in
	 * @param aRoute
	 */
	public void addToRoutesList(Route aRoute){
		this.routesList.add(aRoute);
	}

	/**
	 * Removes a certain Route from the Routes list this Stop exists in
	 * @param aRoute
	 */
	public void removeFromRoutesList(Route aRoute){
		this.routesList.remove(aRoute);
	}

	public List<Route> getRoutesList() {
		return routesList;
	}

	public void setRoutesList(List<Route> routesList) {
		this.routesList = routesList;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public boolean equals(@NonNull Stop aStop){
		if(aStop.getCompanyID().equals(this.getCompanyID()) && aStop.getName().equals(this.getName())){
			return true;
		}
		else{
			return false;
		}
	}

	public String toString(){
		return this.getName();
	}



}
