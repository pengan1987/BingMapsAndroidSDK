package org.bingmaps.rest.models;

import java.util.ArrayList;
import java.util.List;

import org.bingmaps.sdk.Coordinate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RouteLeg {
	public RouteLeg()
    {
    }

    public RouteLeg(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }

    public RouteLeg(String serializedObj) throws JSONException {
        deserialize(serializedObj);
    }

     public double TravelDistance;

     public long TravelDuration;

     public Coordinate ActualStart;

     public Coordinate ActualEnd;

     public Location StartLocation;

     public Location EndLocation;

     public List<ItineraryItem> ItineraryItems;

    private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

    private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.TravelDistance = obj.optDouble("travelDistance");
        this.TravelDuration = obj.optLong("travelDuration");
        
        JSONObject actualStartPointObj = obj.optJSONObject("actualStart");
        
        if(actualStartPointObj != null){
        	JSONArray jsonArray1 = actualStartPointObj.optJSONArray("coordinates"); 
    	    
        	if (jsonArray1 != null && jsonArray1.length() >= 2) { 
        	   this.ActualStart = new Coordinate(jsonArray1.getDouble(0),jsonArray1.getDouble(1)); 
        	}
        }
        
        JSONObject actualEndPointObj = obj.optJSONObject("actualEnd");
        
        if(actualEndPointObj != null){
        	JSONArray jsonArray2 = actualEndPointObj.optJSONArray("coordinates"); 
    	    
        	if (jsonArray2 != null && jsonArray2.length() >= 2) { 
        	   this.ActualEnd = new Coordinate(jsonArray2.getDouble(0),jsonArray2.getDouble(1)); 
        	}
        }
        
        JSONObject startLocation = obj.optJSONObject("startLocation");
        
        if(startLocation != null){
        	this.StartLocation = new Location(startLocation);
        }
        
        JSONObject endLocation = obj.optJSONObject("endLocation");	
        
        if(endLocation != null){
        	this.EndLocation = new Location(endLocation);
        }

        JSONArray items = obj.optJSONArray("itineraryItems");
        
        if(items != null){
        	this.ItineraryItems = new ArrayList<ItineraryItem>();
        	int len = items.length();
        	for (int i=0; i<len; i++){
        		 this.ItineraryItems.add(new ItineraryItem(items.getJSONObject(i)));
      	   } 
        }
    }
}
