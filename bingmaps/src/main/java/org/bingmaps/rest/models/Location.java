package org.bingmaps.rest.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Location extends Resource {
	public Location()
    {
		super();
    }

    public Location(JSONObject obj) throws JSONException {
    	super(obj);
        deserializeFromObj(obj);
    }

    public Location(String serializedObj) throws JSONException {
    	super(serializedObj);
        deserialize(serializedObj);
    }

     public String EntityType;

     public Address Address;

     public int Confidence;

     private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

    private void deserializeFromObj(JSONObject obj) throws JSONException {

        this.EntityType = obj.optString("entityType");
        JSONObject address = obj.optJSONObject("address");
        
        if(address != null){
        	this.Address = new Address(address);
        }

        String confidence = obj.optString("confidence");
        this.Confidence = org.bingmaps.rest.models.Confidence.parse(confidence);
    }	
}
