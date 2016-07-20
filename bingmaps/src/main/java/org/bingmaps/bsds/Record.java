package org.bingmaps.bsds;

import java.sql.Date;
import java.util.Hashtable;
import java.util.Iterator;

import org.bingmaps.sdk.Coordinate;
import org.bingmaps.sdk.Pushpin;
import org.bingmaps.sdk.PushpinOptions;
import org.bingmaps.sdk.Utilities;
import org.json.JSONException;
import org.json.JSONObject;

public class Record {
	public Record(){
		
	}
	
	public Record(JSONObject obj) throws JSONException{
		deserializeFromObj(obj);
	}

	public Coordinate Location;
	
	public org.bingmaps.rest.models.Address Address;
	
	public String DisplayName;
	
	public String Phone;
	
	public Hashtable<String, Object> Metadata;
	
	public Pushpin toPushpin(PushpinOptions options){
		if(Location != null){
			return new Pushpin(Location, options);
		}
		
		return null;
	}
	
	private void deserializeFromObj(JSONObject obj) throws JSONException {
		this.Metadata = new Hashtable<String, Object>();
		
		double lat = Double.NaN, lon = Double.NaN;
		String addressLine = null, primaryCity = null, secondaryCity = null, 
			subdivision = null, countryRegion = null, postalCode = null;
		
		@SuppressWarnings("rawtypes")
		Iterator iter = obj.keys();
	    while(iter.hasNext()){
	        String key = (String)iter.next();
	        String value = obj.getString(key);
	        
	        if(value.equalsIgnoreCase("null")){
	        	value = null;
	        }
	        
	        if(key.equalsIgnoreCase("Latitude")){
	        	lat = Double.parseDouble(value);
	        }else if(key.equalsIgnoreCase("Longitude")){
	        	lon = Double.parseDouble(value);
	        }else if(key.equalsIgnoreCase("AddressLine")){
	        	addressLine = value;
	        }else if(key.equalsIgnoreCase("PrimaryCity")){
	        	primaryCity = value;
	        }else if(key.equalsIgnoreCase("SecondaryCity")){
	        	secondaryCity = value;
	        }else if(key.equalsIgnoreCase("Subdivision")){
	        	subdivision = value;
	        }else if(key.equalsIgnoreCase("CountryRegion")){
	        	countryRegion = value;
	        }else if(key.equalsIgnoreCase("PostalCode")){
	        	postalCode = value;
	        }else if(key.equalsIgnoreCase("DisplayName")){
	        	this.DisplayName = value;
	        }else if(key.equalsIgnoreCase("Phone")){
	        	this.Phone = value;
	        }else if(value != null){
        		Object v = value;
        		
	        	if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){//parse boolean
	        		v = Boolean.parseBoolean(value);
	        	}else if(value.matches("/Date\\(([0-9]+)\\)/")){//Parse Date
	        		value = value.replace("/Date(", "").replace(")/", "");
	        		v = new Date(Long.parseLong(value));
	        	}else if(value.matches("[0-9]{1,9}")){//Parse int
	        		v = Integer.parseInt(value);
	        	}else if(value.matches("[0-9]+.?[0-9]*")){//Parse double
	        		v = Double.parseDouble(value);
	        	}
	        	
	        	this.Metadata.put(key, v);
        	}
	    }

	    if(!Double.isNaN(lat) && !Double.isNaN(lon)){
			this.Location = new Coordinate(lat, lon);
		}
	    
	    if( !Utilities.isNullOrEmpty(addressLine) || 
			!Utilities.isNullOrEmpty(primaryCity) ||
			!Utilities.isNullOrEmpty(secondaryCity) ||
			!Utilities.isNullOrEmpty(subdivision) ||
			!Utilities.isNullOrEmpty(countryRegion) ||
			!Utilities.isNullOrEmpty(postalCode)){
			
			org.bingmaps.rest.models.Address address = new org.bingmaps.rest.models.Address();
			address.AddressLine = addressLine;
			address.Locality = primaryCity;
			address.AdminDistrict = subdivision;
			address.AdminDistrict2 = secondaryCity;
			address.CountryRegion = countryRegion;
			address.PostalCode = postalCode;
			this.Address = address;
		}
    }
}
