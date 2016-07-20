package org.bingmaps.rest.models;

import org.bingmaps.sdk.Utilities;

public class Confidence {
	public final static int High = 0;
	public final static int Medium = 1;
	public final static int Low = 2;
	public final static int Unknown = 3;
	
	public static int parse(String confidence){
		if(!Utilities.isNullOrEmpty(confidence)){
			if(confidence.equalsIgnoreCase("High")){
	        	return High;
	        }else if(confidence.equalsIgnoreCase("Medium")){
	        	return Medium;
	        }else if(confidence.equalsIgnoreCase("Low")){
	        	return Low;
	        }else if(confidence.equalsIgnoreCase("Unknown")){
	        	return Unknown;
	        }
		}
		
		return Unknown;
	}

	public static String toString(int confidence){
		switch(confidence){
			case High:
				return "High";
			case Medium:
				return "Medium";
			case Low:
				return "Low";
			case Unknown:
			default:
				return "Unknown";
		}
	}
}
