import java.util.ArrayList;


public class Company extends Account
{
   
    private ArrayList<Route> routes;
	private ArrayList<Stop> stops;
	private ArrayList<Vehicle> vehicles;

	/**
	 * Empty constructor
	 */
	public Company(){}

		/**
	 * Constructor
	 * @param aName
	 * @param aPassword
	 * @param anID
	 */
	public Company(String aName, String aPassword, String companyID ){
		this.setCompanyID(companyID);
		this.setUsername(aName);
		this.setPassword(aPassword);
		this.setCompany(this);
	}

	/**
	 * Accessor for vehicles
	 * @return vehicles
	 */
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
	/**
	 * Mutator for vehicles
	 * @param vehicles
	 */
	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	/**
	 * Accessor for stops
	 * @return stops
	 */
	public ArrayList<Stop> getStops() {
		return stops;
	}
	/**
	 * Mutator for stops
	 * @param stops
	 */
	public void setStops(ArrayList<Stop> stops) {
		this.stops = stops;
	}
	/**
	 * Accessor for routes
	 * @return
	 */
	public ArrayList<Route> getRoutes() {
		return routes;
	}
	/**
	 * Mutator for routes
	 * @param routes
	 */
	public void setRoutes(ArrayList<Route> routes) {
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






}
