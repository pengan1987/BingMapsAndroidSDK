package org.bingmaps.rest.models;

import org.bingmaps.sdk.Coordinate;
import org.bingmaps.sdk.LocationRect;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Resource {
	public Resource()
    {
    }

    public Resource(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }

    public Resource(String serializedObj) throws JSONException {
    	deserialize(serializedObj);
    }

     public String Name;

     public Coordinate Point;

     public LocationRect BoundingBox;

     private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

     private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.Name = obj.optString("name");
        
        JSONObject pointObj = obj.optJSONObject("point");
        
        if(pointObj != null){
        	JSONArray jsonPointArray = pointObj.optJSONArray("coordinates"); 
    	    
        	if (jsonPointArray != null && jsonPointArray.length() >= 2) { 
        	   this.Point = new Coordinate(jsonPointArray.getDouble(0),jsonPointArray.getDouble(1)); 
        	}
        }
        
        JSONArray boundingBox = obj.optJSONArray("bbox");
        
        if(boundingBox != null && boundingBox.length() >= 4){
        	 double south = boundingBox.getDouble(0);
             double west = boundingBox.getDouble(1);
             double north = boundingBox.getDouble(2);
             double east = boundingBox.getDouble(3);
             
             this.BoundingBox = new LocationRect(north, east, south, west);
        }
    }
}
