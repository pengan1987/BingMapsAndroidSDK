package org.bingmaps.rest;

import org.bingmaps.sdk.Utilities;

public class RouteAvoidType {
	public final static int Highways = 0;
	public final static int Tolls = 1;
	public final static int MinimizeHighways = 2;
	public final static int MinimizeTolls = 3;
	
	public static int parse(String routeAvoidType){
		if(!Utilities.isNullOrEmpty(routeAvoidType)){
			if(routeAvoidType.equalsIgnoreCase("highways")){
	        	return Highways;
	        }else if(routeAvoidType.equalsIgnoreCase("tolls")){
	        	return Tolls;
	        }else if(routeAvoidType.equalsIgnoreCase("minimizeHighways")){
	        	return MinimizeHighways;
	        }else if(routeAvoidType.equalsIgnoreCase("minimizeTolls")){
	        	return MinimizeTolls;
	        }
		}
		
		return -1;
	}

	public static String toString(int confidence){
		switch(confidence){
			case Highways:
				return "highways";
			case Tolls:
				return "tolls";
			case MinimizeHighways:
				return "minimizeHighways";
			case MinimizeTolls:
				return "minimizeTolls";
		}
		
		return null;
	}
}
