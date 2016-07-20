package org.bingmaps.sdk;

/**
 * An object that represents a rectangle on the map.
 */
public class LocationRect {
	/* Private Properties */
	
	private double _north, _south, _east, _west;
	
	/* Constructors */
	
	/**
	 * An object that represents a rectangle on the map.
	 * @param topLeft - Top Left Coordinate
	 * @param bottomRight -Bottom Right Coordinate 
	 */
	public LocationRect(Coordinate topLeft, Coordinate bottomRight){
		_north = topLeft.Latitude;
		_west = topLeft.Longitude;
		_south = bottomRight.Latitude;
		_east = bottomRight.Longitude;
	}

	/**
	 * An object that represents a rectangle on the map.
	 * @param north - Maximum Latitude Coordinate
	 * @param east - Minimum Latitude Coordinate
	 * @param south - Maximum Longitude Coordinate
	 * @param west - Minimum Longitude Coordinate
	 */
	public LocationRect(double north, double east, double south, double west){
		_north = north;
		_east = east;
		_south = south;
		_west = west;
	}
	
	/* Public Properties */
	
	public double getNorth(){
		return _north;
	}
	
	public double getSouth(){
		return _south;
	}
	
	public double getWest(){
		return _west;
	}
	
	public double getEast(){
		return _east;
	}
	
	public Coordinate getTopLeft(){
		return new Coordinate(_north, _west);
	}
	
	public Coordinate getBottomRight(){
		return new Coordinate(_south, _east);
	}
	
	/* Public Methods */
	
	/**
	 * Joins two LocationRect's together and returns a 
	 * new locationRect that encloses both LocationRect's
	 * @param locationRect - LocationRect to join with.
	 */
	public LocationRect join(LocationRect locationRect){
		if(locationRect != null){
			double north = (_north < locationRect.getNorth()) ? locationRect.getNorth() : _north;
			double south = (_south < locationRect.getSouth()) ? locationRect.getSouth() : _south;
			double east = (_east < locationRect.getEast()) ? locationRect.getEast() : _east;
			double west = (_north < locationRect.getWest()) ? locationRect.getWest() : _north;
			return new LocationRect(north, east, south, west);
		}
		
		return this;
	}
	
	public String toString(){
		return "MM.LocationRect.fromCorners(" + getTopLeft().toString() + "," + getBottomRight().toString() + ")";
	}
}
