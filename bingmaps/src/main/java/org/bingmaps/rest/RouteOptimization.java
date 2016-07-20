package org.bingmaps.rest;

import org.bingmaps.sdk.Utilities;

public class RouteOptimization {
	public final static int Time = 0;
	public final static int Distance = 1;
	public final static int TimeWithTraffic = 2;

	public static int parse(String routeOptimization){
		if(!Utilities.isNullOrEmpty(routeOptimization)){
			if(routeOptimization.equalsIgnoreCase("distance")){
	        	return Distance;
	        }else if(routeOptimization.equalsIgnoreCase("timeWithTraffic")){
	        	return TimeWithTraffic;
	        }
		}
		
		return Time;
	}

	public static String toString(int routeOptimization){
		switch(routeOptimization){
			case TimeWithTraffic:
				return "timeWithTraffic";
			case Distance:
				return "distance";
			case Time:
			default:
				return "time";
		}
	}
}
