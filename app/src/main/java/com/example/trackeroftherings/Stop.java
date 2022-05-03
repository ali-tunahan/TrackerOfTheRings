package com.example.trackeroftherings;

import android.location.Location;

import java.util.ArrayList;

public class Stop implements Locatable
{
	private String name;
	private Location location;
	private ArrayList <Route> routesList;
	private String companyID;

	/**
	 * Empty constructor
	 */
	public Stop(){}

	/**
	 * Consturctor
	 * No stop will be created with a route
	 * @param aName
	 * @param aLocation
	 */
	public Stop (String aName, Location aLocation, String aCompanyID)
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
	public void setName (String aName)
	{
		this.name = aName;
	}

	/**
	 * Mutator for location
	 * @param aLocation
	 */
	public void setLocation (Location aLocation)
	{
		this.location = aLocation;
	}

	/**
	 * Adds aRoute to the end of routesList
	 * @param aRoute
	 */
	public void addRoute (Route aRoute)
	{
		this.routesList.add (aRoute);
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
	public Location getLocation()
	{
		return this.location;
	}

	/**
	 * Accessor method for routesList
	 * @return ArrayList<Route> routesList
	 */
	public ArrayList <Route> getRouteList ()
	{
		return this.routesList;
	}

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



}
