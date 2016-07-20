package org.bingmaps.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bingmaps.rest.models.Address;
import org.bingmaps.sdk.Coordinate;
import org.bingmaps.sdk.DistanceUnit;
import org.bingmaps.sdk.Utilities;

public class RouteRequest {
	private List<String> _waypoints;
	
	private int _travelMode = TravelMode.Driving;
	private int _optimize = RouteOptimization.Time;
	private int[] _avoidTypes;
	private int _timeType;
	private Date _dateTime = new Date();
	private int _maxSolutions = 1;
	private int _routePathOutput = RoutePathOutput.None;
	private int _distanceUnit = DistanceUnit.KM;
	private double _tolerance = 0.000003;
	
	public RouteRequest(){
		_waypoints = new ArrayList<String>();
	}
	
	public int getTravelMode(){ return _travelMode; }
	public void setTravelMode(int travelMode){ _travelMode = travelMode; }
	
	public int getOptimize(){ return _optimize; }
	public void setOptimize(int reouteOptimization){ _optimize = reouteOptimization; }
	
	public int[] getRouteAvoidTypes(){ return _avoidTypes; }
	public void setRouteAvoidTypes(int[] avoidTypes){ _avoidTypes = avoidTypes; }
	
	public int getTimeType(){ return _timeType; }
	public void setTimeType(int timeType){ _timeType = timeType; }
	
	public Date getDateTime(){ return _dateTime; }
	public void setDateTime(Date dateTime){ _dateTime = dateTime; }
	
	public double getTolerance(){ return _tolerance; }
	public void setTolerance(double tolerance){ _tolerance = tolerance; }
	
	public int getMaxSolutions(){ return _maxSolutions; }
	public void setMaxSolutions(int maxSolutions){ _maxSolutions = maxSolutions; }
	
	public int getRoutePathOutput(){ return _routePathOutput; }
	public void setRoutePathOutput(int routePathOutput){ _routePathOutput = routePathOutput; }
	
	public int getDistanceUnit(){ return _distanceUnit; }
	public void setDistanceUnit(int distanceUnit){ _distanceUnit = distanceUnit; }

	public void addWaypoint(String query){
		if(!Utilities.isNullOrEmpty(query)){
			_waypoints.add(query);
		}
	}
	
	public void addWaypoint(Address address){
		String a = address.toString();
		
		if(!Utilities.isNullOrEmpty(a)){
			_waypoints.add(a);
		}
	}

	public void addWaypoint(Coordinate coordinate){
		if(coordinate != null){
			_waypoints.add(coordinate.Latitude + "," + coordinate.Longitude);
		}
	}
	
	public void addWaypointQueries(List<String> queries){
		_waypoints.addAll(queries);
	}
	
	public void addWaypointAddresses(List<Address> addresses){
		for(Address a : addresses){
			addWaypoint(a);
		}	
	}
	
	public void addWaypointCoordiantes(List<Coordinate> coordinates){
		for(Coordinate c : coordinates){
			addWaypoint(c);
		}
	}
	
	public void clearWaypoints(){
		_waypoints.clear();
	}

	public List<String> getWaypoints(){
		return _waypoints;
	}
	
	public String getWaypoint(int index){
		if(index < _waypoints.size()){
			return _waypoints.get(index);
		}
		
		return null;
	}

	public String toString(){
		//generate URL
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.BaseServiceURL);
		sb.append("Routes/");

		sb.append(TravelMode.toString(_travelMode));
		sb.append("?");
		
		//Add waypoints
		int len = _waypoints.size();
		for(int i=0; i < len; i++){
			sb.append("wp.");
			sb.append(i);
			sb.append("=");
			sb.append(Utilities.EnodeURLParam(_waypoints.get(i)));
			sb.append("&");
		}
		
		sb.deleteCharAt(sb.length()-1);
		
		if(_travelMode == TravelMode.Driving && _avoidTypes != null && _avoidTypes.length > 0){
			sb.append("&avoid=");
			
			for(int at : _avoidTypes){
				String avoidType = RouteAvoidType.toString(at);
				if(!Utilities.isNullOrEmpty(avoidType)){
					sb.append(avoidType);
					sb.append(",");
				}
			}
			sb.deleteCharAt(sb.length()-1);
		}
		
		sb.append("&optmz=");
		sb.append(RouteOptimization.toString(_optimize));

		if(_routePathOutput == RoutePathOutput.Points){
			sb.append("&rpo=");
			sb.append(RoutePathOutput.toString(_routePathOutput));
			
			sb.append("&tl=");
			sb.append(_tolerance);
		}
		
		sb.append("&du=");
		sb.append(DistanceUnit.toString(_distanceUnit));
		
		if(_travelMode == TravelMode.Transit){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			sb.append("&dt=");
			sb.append(Utilities.EnodeURLParam(dateFormat.format(_dateTime)));
			
			sb.append("&tt=");
			sb.append(TimeType.toString(_timeType));
			
			if(_maxSolutions > 1){
				sb.append("&maxSolns=");
				sb.append(_maxSolutions);
			}
		}
		
		return sb.toString();
	}
}
