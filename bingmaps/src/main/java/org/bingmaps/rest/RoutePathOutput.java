package org.bingmaps.rest;

import org.bingmaps.sdk.Utilities;

public class RoutePathOutput {
	public final static int None = 0;
	public final static int Points = 1;
	
	public static int parse(String routePathOutput){
		if(!Utilities.isNullOrEmpty(routePathOutput)){
			if(routePathOutput.equalsIgnoreCase("None")){
	        	return None;
	        }else if(routePathOutput.equalsIgnoreCase("Points")){
	        	return Points;
	        }
		}
		
		return None;
	}

	public static String toString(int routePathOutput){
		switch(routePathOutput){
			case Points:
				return "Points";
			case None:
			default:
				return "None";
		}
	}
}
