package org.bingmaps.rest.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Response {
	public Response()
    {
    }

    public Response(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }
     
    public int StatusCode;

    public String StatusDescription;

    public ResourceSet[] ResourceSets;

    private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.StatusCode = obj.optInt("statusCode");
        this.StatusDescription = obj.optString("statusDescription");

        JSONArray resourceSets = obj.optJSONArray("resourceSets");
        
        if(resourceSets != null && resourceSets.length() > 0){
        	int len = resourceSets.length();
        	this.ResourceSets = new ResourceSet[len];
        	
        	JSONObject obj2;
        	ResourceSet rs;
        	
        	for (int i=0; i < len; i++){
        		obj2 = resourceSets.optJSONObject(i);
        		if(obj2 != null){
        			rs = new ResourceSet(obj2);
        		}else{
        			rs = null;
        		}
        		
        		this.ResourceSets[i] = rs;
      	    } 
        }
    }
}
