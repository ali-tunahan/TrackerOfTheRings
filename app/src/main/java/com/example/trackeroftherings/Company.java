package com.example.trackeroftherings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Company extends Account implements Serializable
{
   
    private List<Route> routes;
	private List<Stop> stops;
	private List<Vehicle> vehicles;

	/**
	 * Default constructor for database
	 */
	public Company(){
		routes = new ArrayList<Route>();
		stops = new ArrayList<Stop>();
		vehicles = new ArrayList<Vehicle>();
	}

		/**
	 * Constructor
	 * @param aName
	 * @param aPassword
	 * @param companyID
	 */
	public Company(String aName, String aPassword, String companyID ){
		this.setCompanyID(companyID);
		this.setUsername(aName);
		this.setPassword(aPassword);
		routes = new ArrayList<Route>();
		stops = new ArrayList<Stop>();
		vehicles = new ArrayList<Vehicle>();
	}

	/**
	 * Accessor for vehicles
	 * @return vehicles
	 */
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	/**
	 * Mutator for vehicles
	 * @param vehicles
	 */
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	/**
	 * Accessor for stops
	 * @return stops
	 */
	public List<Stop> getStops() {
		return stops;
	}
	/**
	 * Mutator for stops
	 * @param stops
	 */
	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}
	/**
	 * Accessor for routes
	 * @return
	 */
	public List<Route> getRoutes() {
		return routes;
	}
	/**
	 * Mutator for routes
	 * @param routes
	 */
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	/**
	 * Adds a route to company routes
	 * @param newRoute
	 */
	public void addRoute(Route newRoute){
		routes.add(newRoute);
	}

	/**
	 * Adds a stop to company stops
	 * @param newStop
	 */
	public void addStop(Stop newStop){
		stops.add(newStop);
	}

	/**
	 * Adds a vehicle to company vehicles
	 * @param newVehicle
	 */
	public void addVehicle(Vehicle newVehicle){
		vehicles.add(newVehicle);
	}

	/**
	 * Adds a route to company routes
	 * @param newRoute
	 */
	public void removeRoute(Route newRoute){
		routes.remove(newRoute);
	}

	/**
	 * Adds a stop to company stops
	 * @param newStop
	 */
	public void removeStop(Stop newStop){
		stops.remove(newStop);
	}

	/**
	 * Adds a vehicle to company vehicles
	 * @param newVehicle
	 */
	public void removeVehicle(Vehicle newVehicle){
		vehicles.remove(newVehicle);
	}

	public boolean equals(Company aCompany){
		if(aCompany.getCompanyID().equals(this.getCompanyID())&& aCompany.getUsername().equals(this.getUsername())){
			return true;
		}
		else{
			return false;
		}
	}




}
