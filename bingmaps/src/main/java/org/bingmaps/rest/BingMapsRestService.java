package org.bingmaps.rest;

import org.bingmaps.data.ContentTypes;
import org.bingmaps.data.RequestType;
import org.bingmaps.data.ServiceRequest;
import org.bingmaps.sdk.Coordinate;
import org.bingmaps.sdk.Utilities;

import android.os.Handler;

public class BingMapsRestService {
	public Handler GeocodeAsyncCompleted;
	public Handler ReverseGeocodeAsyncCompleted;
	public Handler RouteAsyncCompleted;
	public Handler MajorRouteAsyncCompleted;
	
	private String _bingMapsKey;
	private String _culture;
	
	public BingMapsRestService(String bingMapsKey){
		_bingMapsKey = bingMapsKey;
	}
	
	public BingMapsRestService(String bingMapsKey, String culture){
		_bingMapsKey = bingMapsKey;
		_culture = culture;
	}
	
	/* Public Methods */
	
	public void GeocodeAsync(String query){
		//generate URL
		String requestURL = Constants.BaseServiceURL + "Locations/" + Utilities.EnodeURLParam(query) + "?";
		
		if(!Utilities.isNullOrEmpty(_culture)){
			requestURL += "c=" + _culture + "&";
		}
		
		requestURL += "key=" + _bingMapsKey;
		
		//create service request
		ServiceRequest request = new ServiceRequest(requestURL, RequestType.GET, ContentTypes.JSON);
		LocationServiceAsyncTask service = new LocationServiceAsyncTask(GeocodeAsyncCompleted);
		service.execute(request);
	}
	
	public void GeocodeAsync(org.bingmaps.rest.models.Address address){
		//generate URL
		String requestURL = Constants.BaseServiceURL + "Locations?";
		
		StringBuilder sb = new StringBuilder();
		
		if(address != null){
			if(!Utilities.isNullOrEmpty(address.AddressLine)){
				sb.append("&addressLine=");
				sb.append(Utilities.EnodeURLParam(address.AddressLine));
			}

			if(!Utilities.isNullOrEmpty(address.Locality)){
				sb.append("&locality=");
				sb.append(Utilities.EnodeURLParam(address.Locality));
			}
			
			if(!Utilities.isNullOrEmpty(address.AdminDistrict)){
				sb.append("&adminDistrict=");
				sb.append(Utilities.EnodeURLParam(address.AdminDistrict));
			}
			
			if(!Utilities.isNullOrEmpty(address.PostalCode)){
				sb.append("&postalCode=");
				sb.append(Utilities.EnodeURLParam(address.PostalCode));
			}
			
			if(!Utilities.isNullOrEmpty(address.CountryRegion)){
				sb.append("&countryRegion=");
				sb.append(Utilities.EnodeURLParam(address.CountryRegion));
			}
		}
		
		if(!Utilities.isNullOrEmpty(_culture)){
			sb.append("&c=");
			sb.append(_culture);
		}
		
		sb.append("&key=");
		sb.append(_bingMapsKey);
		
		requestURL += sb.substring(1);
		
		//create service request
		ServiceRequest request = new ServiceRequest(requestURL, RequestType.GET, ContentTypes.JSON);
		LocationServiceAsyncTask service = new LocationServiceAsyncTask(GeocodeAsyncCompleted);
		service.execute(request);
	}

	//includeEntityTypes not supported
	public void ReverseGeocodeAsync(Coordinate coordinate){
		//generate URL
		String requestURL = Constants.BaseServiceURL + "Locations/" 
							+ coordinate.Latitude + "," + coordinate.Longitude + "?";
		
		if(!Utilities.isNullOrEmpty(_culture)){
			requestURL += "c=" + _culture + "&";
		}
		
		requestURL += "key=" + _bingMapsKey;
		
		//create service request
		ServiceRequest request = new ServiceRequest(requestURL, RequestType.GET, ContentTypes.JSON);
		LocationServiceAsyncTask service = new LocationServiceAsyncTask(ReverseGeocodeAsyncCompleted);
		service.execute(request);
	}
	
	public void RouteAsync(RouteRequest routeRequest){
		
		String requestURL = routeRequest.toString();
		
		if(!Utilities.isNullOrEmpty(_culture)){
			requestURL += "&c=" + _culture;
		}
		
		requestURL += "&key=" + _bingMapsKey;
		
		//create service request
		ServiceRequest request = new ServiceRequest(requestURL, RequestType.GET, ContentTypes.JSON);
		RouteServiceAsyncTask service = new RouteServiceAsyncTask(RouteAsyncCompleted);
		service.execute(request);
	}
	
	public void MajorRouteAsync(MajorRouteRequest routeRequest){
		String requestURL = routeRequest.toString();
		
		if(!Utilities.isNullOrEmpty(_culture)){
			requestURL += "&c=" + _culture;
		}
		
		requestURL += "&key=" + _bingMapsKey;
		
		//create service request
		ServiceRequest request = new ServiceRequest(requestURL, RequestType.GET, ContentTypes.JSON);
		RouteServiceAsyncTask service = new RouteServiceAsyncTask(MajorRouteAsyncCompleted);
		service.execute(request);
	}
}
