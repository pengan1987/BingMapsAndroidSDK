package org.bingmaps.rest.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Route extends Resource {
	public Route()
    {
		super();
    }

    public Route(JSONObject obj) throws JSONException {
    	super(obj);
        deserializeFromObj(obj);
    }

     public int DistanceUnit;

     public int DurationUnit;

     public double TravelDistance;

     public long TravelDuration;

     public List<RouteLeg> RouteLegs;

     public String RoutePath = "[]";
     
     private void deserializeFromObj(JSONObject obj) throws JSONException {
        String distanceUnit = obj.optString("distanceUnit");
        this.DistanceUnit = org.bingmaps.sdk.DistanceUnit.parse(distanceUnit);
        
        String durationUnit = obj.optString("durationUnit");
        this.DurationUnit = org.bingmaps.rest.models.DurationUnit.parse(durationUnit);
        
        this.TravelDistance = obj.optDouble("travelDistance");
        this.TravelDuration = obj.optLong("travelDuration");
        
    	JSONArray routeLegs = obj.optJSONArray("routeLegs");
        
        if(routeLegs != null){
        	this.RouteLegs = new ArrayList<RouteLeg>();
        	int len = routeLegs.length();
        	for (int i=0; i<len; i++){
 			   this.RouteLegs.add(new RouteLeg(routeLegs.getJSONObject(i))); 
 	   		} 
        }
    }
}
