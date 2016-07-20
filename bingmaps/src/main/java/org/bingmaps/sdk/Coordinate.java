package org.bingmaps.sdk;

/**
 * An object that represents a coordinate on the map. 
 * The name "Coordinate" was used instead of "Location" 
 * so as not to interfere with the GPS Location object.
 * @author Ricky Brundritt
 */
public class Coordinate {
	/* Constructor */
	
	/**
	 * Constructor
	 */
	public Coordinate(){}
	
	/**
	 * Constructor
	 * @param latitude Latitude coordinate
	 * @param longitude Longitude coordinate
	 */
	public Coordinate(double latitude, double longitude){
		this.Latitude = latitude;
		this.Longitude = longitude;
	}
	
	/* Public Properties */
	
	/**
	 * Latitude Coordinate
	 */
	public double Latitude;
	
	/**
	 * Longitude Coordinate
	 */
	public double Longitude;
	
	/* Public Methods */
	
	/**
	 * Returns a JSON representation of the Coordinate object for use with Bing Maps.
	 */
	public String toString()
	{
		return "new MM.Location(" + Latitude + "," + Longitude + ")";
	}
}
