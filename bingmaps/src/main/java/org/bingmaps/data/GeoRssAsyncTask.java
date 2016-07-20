package org.bingmaps.data;

import java.io.ByteArrayInputStream;
import java.util.List;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class GeoRssAsyncTask extends AsyncTask<ServiceRequest, Void, List<GeoRssItem>>  {
	private Handler _callback;
	
	public GeoRssAsyncTask(Handler callback) {
		_callback = callback;
	}
	
	protected List<GeoRssItem> doInBackground(ServiceRequest... params) {
		List<GeoRssItem> items = null;
		if(params != null && params.length > 0){
			ServiceRequest request = params[0];
			String result = request.execute();
			
			try {
				GeoRssFeedParser parser = new GeoRssFeedParser();
				items = parser.parse(new ByteArrayInputStream(result.getBytes()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return items;
	}
	
	@Override
	 protected void onPostExecute(final List<GeoRssItem> items) {	
		 if(_callback != null){
			 Message response = new Message();
			 response.obj = items;
			 _callback.sendMessage(response);
		 }
	 }
}
