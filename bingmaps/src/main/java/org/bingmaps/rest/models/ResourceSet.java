package org.bingmaps.rest.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResourceSet {
	public ResourceSet()
    {
    }

    public ResourceSet(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }

    public ResourceSet(String serializedObj) throws JSONException {
        deserialize(serializedObj);
    }

     public long EstimatedTotal;

     public JSONObject[] Resources;

    private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

	private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.EstimatedTotal = obj.optLong("estimatedTotal");

        JSONArray resources = obj.optJSONArray("resources");
        
        if(resources != null){
        	int len = resources.length();
        	this.Resources = new JSONObject[len];
        	 for (int i = 0; i < len; i++){
        		 this.Resources[i] = resources.optJSONObject(i);
      	   } 
        }
    }
}
