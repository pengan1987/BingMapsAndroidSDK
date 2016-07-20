package org.bingmaps.sdk;

/**
 * A class for representing Distance Units. This class is faster than using enumerators.
 * @author rbrundritt
 */
public class DistanceUnit {
	/* Public Constant values */
	
	/**
	 * Kilometer id
	 */
	public final static int KM = 0;
	
	/**
	 * Mile id
	 */
	public final static int Mile = 1;
	
	/* Public Methods */
	
	/**
	 * Parses a string distance unit and returns the distance unit id.
	 * @param distanceUnit A string distance unit
	 * @return Returns a distance unit id value for the string. Defaults to Kilometer.
	 */
	public static int parse(String distanceUnit){
		if(!Utilities.isNullOrEmpty(distanceUnit) && !distanceUnit.equalsIgnoreCase("Kilometer")){
        	return Mile;
        }else{
        	return KM;
        }
	}
	
	/**
	 * Converts a distance unit id into a string.
	 * @param distanceUnit Distance unit id value.
	 * @return A string value of the distance unit.
	 */
	public static String toString(int distanceUnit){
		switch(distanceUnit){
			case Mile:
				return "Mile";
			case KM:
			default:
				return "Kilometer";
		}
	}
}
