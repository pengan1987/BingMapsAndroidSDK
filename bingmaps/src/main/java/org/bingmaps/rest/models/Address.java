package org.bingmaps.rest.models;

import org.bingmaps.sdk.Utilities;
import org.json.JSONException;
import org.json.JSONObject;

public class Address {
	public Address()
    {
    }

    public Address(JSONObject obj) throws JSONException {
        deserializeFromObj(obj);
    }

    public Address(String serializedObj) throws JSONException {
        deserialize(serializedObj);
    }
    
    public String AddressLine;

	public String AdminDistrict;

	public String AdminDistrict2;

	public String CountryRegion;

	public String FormattedAddress;

	public String Locality;

	public String PostalCode;

	public String toString(){
		if(!Utilities.isNullOrEmpty(FormattedAddress)){
			return FormattedAddress;
		}else{
			StringBuilder sb = new StringBuilder();
			
			if(!Utilities.isNullOrEmpty(AddressLine)){
				sb.append(" ");
				sb.append(AddressLine);
			}
			
			if(!Utilities.isNullOrEmpty(Locality)){
				sb.append(", ");
				sb.append(Locality);
			}
			
			if(!Utilities.isNullOrEmpty(AdminDistrict)){
				sb.append(", ");
				sb.append(AdminDistrict);
			}
			
			if(!Utilities.isNullOrEmpty(PostalCode)){
				sb.append("  ");
				sb.append(PostalCode);
			}
			
			if(!Utilities.isNullOrEmpty(CountryRegion)){
				sb.append("  ");
				sb.append(CountryRegion);
			}
			
			String s = "";
			
			if(sb.length() > 2){
				s = sb.substring(2, sb.length()-1).replace("  ", " ");
			}
			
			return s;
		}
	}
	 
    private void deserialize(String serializedObj) throws JSONException {
        JSONObject obj = new JSONObject(serializedObj);
        deserializeFromObj(obj);
    }

    private void deserializeFromObj(JSONObject obj) throws JSONException {
        this.AddressLine = obj.optString("addressLine");
        this.AdminDistrict = obj.optString("adminDistrict");
        this.AdminDistrict2 = obj.optString("adminDistrict2");
        this.CountryRegion = obj.optString("countryRegion");
        this.FormattedAddress = obj.optString("formattedAddress");
        this.Locality = obj.optString("locality");
        this.PostalCode = obj.optString("postalCode");
    }
}
