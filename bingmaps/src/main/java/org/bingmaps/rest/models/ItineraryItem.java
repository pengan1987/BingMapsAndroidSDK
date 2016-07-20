package org.bingmaps.rest.models;

import org.bingmaps.sdk.Coordinate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItineraryItem {
	public ItineraryItem()
    {
    }

    public ItineraryItem(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }

    public ItineraryItem(String serializedObj) throws JSONException {
        deserialize(serializedObj);
    }

     public String TravelMode;

     public double TravelDistance;

     public long TravelDuration;

     public Coordinate ManeuverPoint;

     public Instruction Instruction;

     public String CompassDirection;

     public Hint[] Hint;

     public Warning[] Warning;

    private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

    private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.TravelMode = obj.optString("travelMode");
        this.TravelDistance = obj.optDouble("travelDistance");
        this.TravelDuration = obj.optLong("travelDuration");
        
        JSONObject maneuverPoint = obj.getJSONObject("maneuverPoint");
        
        if(maneuverPoint != null){
        	JSONArray jsonArray = maneuverPoint.optJSONArray("coordinates"); 
    	    
        	if (jsonArray != null && jsonArray.length() >= 2) { 
        	   this.ManeuverPoint = new Coordinate(jsonArray.getDouble(0),jsonArray.getDouble(1)); 
        	}
        }
        
        JSONObject instruction = obj.getJSONObject("instruction");
        this.Instruction = new Instruction(instruction);
        
        this.CompassDirection = obj.optString("compassDirection");
        
        JSONArray hints = obj.optJSONArray("hint");
        if (hints != null) { 
        	this.Hint = new Hint[hints.length()]; 
        	for (int i=0;i<hints.length();i++){ 
        		this.Hint[i] = new Hint(hints.getJSONObject(i)); 
        	} 
     	}
        
        JSONArray warnings = obj.optJSONArray("warning");
        if (warnings != null) { 
        	this.Warning = new Warning[warnings.length()]; 
        	for (int i=0;i<warnings.length();i++){ 
        		this.Warning[i] = new Warning(warnings.getJSONObject(i)); 
        	} 
     	}
    }
}
