package org.bingmaps.bsds;

import org.bingmaps.data.ServiceRequest;
import org.bingmaps.sdk.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class QueryServiceAsyncTask extends AsyncTask<ServiceRequest, Void, Record[]> {
	private Handler _callback;
	
	public QueryServiceAsyncTask(Handler callback) {
		_callback = callback;
	}

	@Override
	protected Record[] doInBackground(ServiceRequest... params) {
		Record[] records = null;
		if(params != null && params.length > 0){
			ServiceRequest request = params[0];
			String result = request.execute();
			
			if(!Utilities.isNullOrEmpty(result)){
				try {
					JSONObject obj = new JSONObject(result);
					
					JSONObject root = obj.optJSONObject("d");
					
					if(root != null){
						JSONArray results = root.optJSONArray("results");
						
						if(results != null && results.length() > 0){
							int len = results.length();
							records = new Record[len];
						
							JSONObject obj2;
							Record r;
				        	
				        	for (int i=0; i < len; i++){
				        		obj2 = results.optJSONObject(i);
				        		if(obj2 != null){
				        			r = new Record(obj2);
				        		}else{
				        			r = null;
				        		}
				        		
				        		records[i] = r;
				      	    } 
						}					
					}			
				} catch (JSONException e) {
					e.printStackTrace();
				}	
			}
		}
		
		return records;
	}
	
	@Override
	 protected void onPostExecute(final Record[] locations) {	
		 if(_callback != null){			
			 Message response = new Message();
			 response.obj = locations;
			 _callback.sendMessage(response);
		 }
	 }
}
