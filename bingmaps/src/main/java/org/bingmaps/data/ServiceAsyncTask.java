package org.bingmaps.data;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class ServiceAsyncTask extends AsyncTask<ServiceRequest, Void, String> {
	private Handler _callback;
	
	public ServiceAsyncTask(Handler callback){
		_callback = callback;
	}
	
	@Override
	protected String doInBackground(ServiceRequest... params) {
		String result = "";
		if(params != null && params.length > 0){
			ServiceRequest request = params[0];
			result = request.execute();
		}
		
		return result;
	}
	
	 @Override
	 protected void onPostExecute(final String results) {	
		 if(_callback != null){
			 Message response = new Message();
			 response.obj = results;
			 _callback.sendMessage(response);
		 }
	 }
}
