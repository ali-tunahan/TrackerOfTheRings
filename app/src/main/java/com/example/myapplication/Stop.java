import java.util.ArrayList;

public class Stop //implements Locatable
{
	private String name ;
	private Location location ;
	private ArrayList <Route> routesList ;

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
	public Stop (String aName, Location aLocation)
	{
		this.name = aName ;
		this.location = aLocation ;
		this.routesList = new ArrayList <Route> () ;
	}

	/**
	 * Mutator for name
	 * @param aName
	 */
	public void setName (String aName)
	{
		this.name = aName ;
	}

	/**
	 * Mutator for location
	 * @param aLocation
	 */
	public void setLocation (Location aLocation)
	{
		this.location = aLocation ;
	}

	/**
	 * Adds aRoute to the end of routesList
	 * @param aRoute
	 */
	public void addRoute (Route aRoute)
	{
		this.routesList.add (aRoute) ;
	}

	/**
	 * Removes aRoute form routesList
	 * @param aRoute
	 */
	public void removeRoute (Route aRoute)
	{
		this.routesList.remove (aRoute) ;
	}

	/**
	 * Accessor method for name
	 * @return name
	 */
	public String getName ()
	{
		return this.name ;
	}

	/**
	 * Accessor method for location
	 * @return location
	 */
	public Location getLocation()
	{
		return this.location ;
	}

	public ArrayList <Route> getRouteList ()
	{
		return this.routesList ;
	}

	public void addToRoutesList(Route aRoute){
		this.routesList.add(aRoute);
	}
	public void removeFromRoutesList(Route aRoute){
		this.routesList.remove(aRoute);
	}


}
