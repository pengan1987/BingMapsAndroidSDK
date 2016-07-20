package org.bingmaps.rest;

import org.bingmaps.rest.models.Address;
import org.bingmaps.sdk.Coordinate;
import org.bingmaps.sdk.DistanceUnit;
import org.bingmaps.sdk.Utilities;

public class MajorRouteRequest {
	private String _destination;
	private boolean _excludeRoutes;
	private int _routePathOutput = RoutePathOutput.None;
	private int _distanceUnit = DistanceUnit.KM;
	
	public MajorRouteRequest(){
	}
	
	public int getDistanceUnit(){ return _distanceUnit; }
	public void setDistanceUnit(int distanceUnit){ _distanceUnit = distanceUnit; }
	
	public int getRoutePathOutput(){ return _routePathOutput; }
	public void setRoutePathOutput(int routePathOutput){ _routePathOutput = routePathOutput; }

	public boolean getExcludeRoutes(){ return _excludeRoutes; }
	public void setExcludeRoutes(boolean excludeRoutes){ _excludeRoutes = excludeRoutes; }

	public void setDestination(String query){
		if(!Utilities.isNullOrEmpty(query)){
			_destination = query;
		}
	}
	
	public void setDestination(Address address){
		String a = address.toString();
		
		if(!Utilities.isNullOrEmpty(a)){
			_destination = a;
		}
	}

	public void setDestination(Coordinate coordinate){
		if(coordinate != null){
			_destination = coordinate.Latitude + "," + coordinate.Longitude;
		}
	}
	
	public String getDestination(){return _destination;}
	
	public String toString(){
		//generate URL
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BaseServiceURL);
		sb.append("Routes/FromMajorRoads?dest=");
		sb.append(Utilities.EnodeURLParam(_destination));

		if(_excludeRoutes){
			sb.append("&excl=routes");
		}

		if(_routePathOutput == RoutePathOutput.Points){
			sb.append("&rpo=");
			sb.append(RoutePathOutput.toString(_routePathOutput));
		}
		
		sb.append("&du=");
		sb.append(DistanceUnit.toString(_distanceUnit));
		
		return sb.toString();
	}
}
