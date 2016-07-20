package org.bingmaps.rest;

import org.bingmaps.sdk.Utilities;

public class TimeType {
	public final static int Arrival = 0;
	public final static int Departure = 1;
	public final static int LastAvailable = 2;
	
	public static int parse(String timeType){
		if(!Utilities.isNullOrEmpty(timeType)){
			if(timeType.equalsIgnoreCase("Arrival")){
	        	return Arrival;
	        }else if(timeType.equalsIgnoreCase("Departure")){
	        	return Departure;
	        }else if(timeType.equalsIgnoreCase("LastAvailable")){
	        	return LastAvailable;
	        }
		}
		
		return -1;
	}

	public static String toString(int timeType){
		switch(timeType){
			case Arrival:
				return "Arrival";
			case Departure:
				return "Departure";
			case LastAvailable:
				return "LastAvailable";
			default:
				return null;
		}
	}
}
