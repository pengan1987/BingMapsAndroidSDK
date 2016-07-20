package org.bingmaps.rest;

import org.bingmaps.data.ServiceRequest;
import org.bingmaps.rest.models.Location;
import org.bingmaps.rest.models.ResourceSet;
import org.bingmaps.rest.models.Response;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class LocationServiceAsyncTask extends AsyncTask<ServiceRequest, Void, Location[]> {
	private Handler _callback;
	
	public LocationServiceAsyncTask(Handler callback) {
		_callback = callback;
	}
	
	@Override
	protected Location[] doInBackground(ServiceRequest... params) {
		Location[] locations = null;
		if(params != null && params.length > 0){
			ServiceRequest request = params[0];
			String result = request.execute();
			
			try {
				JSONObject obj = new JSONObject(result);
				Response response = new Response(obj);
				if(response.ResourceSets.length > 0){
					ResourceSet resourceSet = response.ResourceSets[0];
					int len = resourceSet.Resources.length;
					locations = new Location[len];

					for(int i = 0; i < len; i++)
					{
						obj = resourceSet.Resources[i];
						locations[i] = new Location(obj);
					}
				}						
			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}
		
		return locations;
	}
	
	@Override
	 protected void onPostExecute(final Location[] locations) {	
		 if(_callback != null){			
			 Message response = new Message();
			 response.obj = locations;
			 _callback.sendMessage(response);
		 }
	 }
}
