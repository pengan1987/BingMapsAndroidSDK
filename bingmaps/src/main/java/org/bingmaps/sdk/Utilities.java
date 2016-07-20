package org.bingmaps.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A set of useful reusable tools.
 * @author rbrundritt
 */
public class Utilities {
		
	/**
	 * Determines if a string value is null or empty.
	 * @param value String value to check
	 * @return A boolean indicating is the string value is null or empty.
	 */
	public static boolean isNullOrEmpty(String value){
		return value == null || value.length() == 0;
	}
	
	/**
	 * Encodes URL parameters for use with HTTP requests. Replaces spaces 
	 * with %20 rather than + as is normally done in Java.
	 * @param param URL parameters to encode.
	 * @return Encoded URL parameters that use %20 for spaces rather than +
	 */
	public static String EnodeURLParam(String param){
		try {
			return URLEncoder.encode(param, "utf-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return param;
	}
	
	/**
	 * Converts a JSON string of 2D of double  numbers into a list of coordinates. 
	 * @param doubleArray JSON string of 2D array to convert to list of coordinates.
	 * @return A list of Coordinates.
	 */
	public static List<Coordinate> string2DDoubleArrayToCoordinateList(String doubleArray){
		double lat, lon;
		List<Coordinate> coords = new ArrayList<Coordinate>();
		 
	   	Pattern p = Pattern.compile("(-?[0-9]+.?[0-9]*),(-?[0-9]+.?[0-9]*)");
	    Matcher m = p.matcher(doubleArray); 
	   	while(m.find()) {
	   		 lat = Double.parseDouble(m.group(1));
	   		 lon = Double.parseDouble(m.group(2));
	   		coords.add(new Coordinate(lat, lon));
	   	}
	   	 
	   	return coords;
	}
	
	/**
	 * Converts a List of Coordinates into a JSON string array of Bing Maps Locations.
	 * @param locations List of coordinates to convert into a JSON string.
	 * @return A JSON string of an array of Bing Maps Locations.
	 */
	public static String LocationsToString(List<Coordinate> locations){
		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for(Coordinate c : locations){
			sb.append(c.toString());
			sb.append(",");
		}
		
		sb.deleteCharAt(sb.length() - 1);
		
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * Calculate the distance between two coordinates for a given spherical radius.
	 * @param origin First coordinate to calculate distance between<
	 * @param destination Second coordinate to calculate distance between
	 * @param radius A double that represents the radius of the sphere in which the coordinates lie. e.g. Earth Radius.
	 * @return A distance in the specified units.
	 */
	public static double Haversine(Coordinate origin, Coordinate destination, double radius)
    {
        double dLat = Math.toRadians(destination.Latitude - origin.Latitude);
        double dLon = Math.toRadians(destination.Longitude - origin.Longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(origin.Latitude)) *
            Math.cos(Math.toRadians(origin.Latitude)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radius * c;
    }
}
