package org.bingmaps.bsds;

import java.util.List;

import org.bingmaps.data.ContentTypes;
import org.bingmaps.data.RequestType;
import org.bingmaps.data.ServiceRequest;
import org.bingmaps.sdk.Coordinate;
import org.bingmaps.sdk.LocationRect;
import org.bingmaps.sdk.Utilities;

import android.os.Handler;

public class BingSpatialDataService {
	public Handler FindByAreaCompleted;
	public Handler FindByIDCompleted;
	public Handler FindByPropertyCompleted;

	private String _queryKey;
	private String _serviceURL;
	
	public BingSpatialDataService(String accessId, String dataSourceName, String entityTypeName, String queryKey){
		_queryKey = queryKey;		
		_serviceURL = "http://spatial.virtualearth.net/REST/v1/data/" + accessId + "/" + dataSourceName + "/" + entityTypeName;
	}

	/* Public Methods */
	
	//http://spatial.virtualearth.net/REST/v1/data/accessId/dataSourceName/entityTypeName?spatialFilter=nearby(latitude,longitude,distance)&queryoption1&queryoption2&queryoptionN&key=queryKey
	public void FindByArea(Coordinate center, double distance, QueryOption options){
		StringBuilder sb = new StringBuilder();
		sb.append(_serviceURL);
		
		//Add spatial filter
		sb.append("?spatialFilter=nearby(");
		sb.append(center.Latitude);
		sb.append(",");
		sb.append(center.Longitude);
		sb.append(",");
		sb.append(distance);
		sb.append(")");
		
		//Add Query Options
		if(options != null){
			sb.append(options.toString());
		}else{
			sb.append("&$format=json");
		}
		
		//Add Bing Maps Key
		sb.append("&key=");
		sb.append(_queryKey);

		//create service request
		ServiceRequest request = new ServiceRequest(sb.toString(), RequestType.GET, ContentTypes.JSON);
		QueryServiceAsyncTask service = new QueryServiceAsyncTask(FindByAreaCompleted);
		service.execute(request);
	}
	
	//http://spatial.virtualearth.net/REST/v1/data/accessId/dataSourceName/entityTypeName?spatialFilter=bbox(southLatitude,westLongitude,northLatitude,eastLongitude)&queryOption1&queryOption2&queryOptionN)&key=queryKey
	public void FindByArea(LocationRect boundingBox, QueryOption options){
		StringBuilder sb = new StringBuilder();
		sb.append(_serviceURL);
		
		//Add spatial filter
		sb.append("?spatialFilter=bbox(");
		sb.append(boundingBox.getSouth());
		sb.append(",");
		sb.append(boundingBox.getWest());
		sb.append(",");
		sb.append(boundingBox.getNorth());
		sb.append(",");
		sb.append(boundingBox.getEast());
		sb.append(")");
		
		//Add Query Options
		if(options != null){
			sb.append(options.toString());
		}else{
			sb.append("&$format=json");
		}
		
		//Add Bing Maps Key
		sb.append("&key=");
		sb.append(_queryKey);

		//create service request
		ServiceRequest request = new ServiceRequest(sb.toString(), RequestType.GET, ContentTypes.JSON);
		QueryServiceAsyncTask service = new QueryServiceAsyncTask(FindByAreaCompleted);
		service.execute(request);
	}
	
	//http://spatial.virtualearth.net/REST/v1/data/accessId/dataSourceName/entityTypeName(entityId)&key=queryKey
	public void FindByID(String entityID){
		if(!Utilities.isNullOrEmpty(entityID) && FindByIDCompleted != null){
			StringBuilder sb = new StringBuilder();
			sb.append(_serviceURL);
			
			//Add query
			sb.append("('");
			sb.append(entityID);
			sb.append("')");
			
			sb.append("&$format=json");
			
			//Add Bing Maps Key
			sb.append("&key=");
			sb.append(_queryKey);
	
			//create service request
			ServiceRequest request = new ServiceRequest(sb.toString(), RequestType.GET, ContentTypes.JSON);
			QueryServiceAsyncTask service = new QueryServiceAsyncTask(FindByAreaCompleted);
			service.execute(request);
		}else{
			if(FindByIDCompleted != null){
				 FindByIDCompleted.sendMessage(null);
			}
		}
	}
	
	//http://spatial.virtualearth.net/REST/v1/data/accessId/dataSourceName/entityTypeName?$filter=entityId in (entityId1,entityId2,entityIdN)&queryoption1&queryoption2&queryoptionN&key=queryKey
	/**
	 * Note that the filter property of the QueryOption object is ignored by this method.
	 */
	public void FindByID(List<String> entityIDs, QueryOption options){
		if(entityIDs != null && entityIDs.size() > 0 && FindByIDCompleted != null){
			StringBuilder sb = new StringBuilder();
			sb.append(_serviceURL);
			
			//Add query
			sb.append("?$filter=entityId in (");
			for(String entityID : entityIDs){
				sb.append("'");
				sb.append(entityID);
				sb.append("'");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			
			//Add Query Options
			if(options != null){
				options.Filters = null; //Make Filters null so that it is ignored
				sb.append(options.toString());
			}else{
				sb.append("&$format=json");
			}
			
			//Add Bing Maps Key
			sb.append("&key=");
			sb.append(_queryKey);
	
			//create service request
			ServiceRequest request = new ServiceRequest(sb.toString(), RequestType.GET, ContentTypes.JSON);
			QueryServiceAsyncTask service = new QueryServiceAsyncTask(FindByAreaCompleted);
			service.execute(request);
		}else{
			if(FindByIDCompleted != null){
				 FindByIDCompleted.sendMessage(null);
			}
		}
	}
	
	//http://spatial.virtualearth.net/REST/v1/data/accessId/dataSourceName/entityTypeName?$filter=filterString&queryOption1&queryOption2&queryOptionN&jsonp=jsonCallBackFunction&jsonso=jsonState&key=queryKey
	public void FindByProperty(QueryOption options){
		if(options != null && !Utilities.isNullOrEmpty(options.Filters)){
			StringBuilder sb = new StringBuilder();
			sb.append(_serviceURL);

			//Add Query Options
			
			sb.append(options.toString());

			//Add Bing Maps Key
			sb.append("&key=");
			sb.append(_queryKey);
	
			//create service request
			ServiceRequest request = new ServiceRequest(sb.toString(), RequestType.GET, ContentTypes.JSON);
			QueryServiceAsyncTask service = new QueryServiceAsyncTask(FindByAreaCompleted);
			service.execute(request);
		}else{
			if(FindByPropertyCompleted != null){
				FindByPropertyCompleted.sendMessage(null);
			}
		}
	}
}
