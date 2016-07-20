package org.bingmaps.rest;

import org.bingmaps.sdk.Utilities;

public class TravelMode {
	public final static int Driving = 0;
	public final static int Transit = 1;
	public final static int Walking = 2;
	
	public static int parse(String travelMode){
		if(!Utilities.isNullOrEmpty(travelMode)){
			if(travelMode.equalsIgnoreCase("Walking")){
	        	return Walking;
	        }else if(travelMode.equalsIgnoreCase("Transit")){
	        	return Transit;
	        }
		}
		
		return Driving;
	}

	public static String toString(int travelMode){
		switch(travelMode){
			case Walking:
				return "Walking";
			case Transit:
				return "Transit";
			case Driving:
			default:
				return "Driving";
		}
	}
}
