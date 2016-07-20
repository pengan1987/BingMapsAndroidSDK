package org.bingmaps.app;

import org.bingmaps.sdk.Coordinate;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GPSManager {
	private LocationManager _locationManager;
	private String _bestProvider;
	
	public GPSManager(Activity activity, LocationListener listener)
	{
		_locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
		if(_bestProvider == null)
		{
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			_bestProvider = _locationManager.getBestProvider(criteria, false);
		}
		
		if(_bestProvider != null)
		{
			_locationManager.requestLocationUpdates(_bestProvider, Constants.GPSTimeDelta, Constants.GPSDistanceDelta, listener);
		}
	}

	public Coordinate GetCoordinate(){
		if(_bestProvider != null)
		{
			Location location = _locationManager.getLastKnownLocation(_bestProvider);
			if(location != null)
			{
				double lat = location.getLatitude();
				double lon = location.getLongitude();
				return new Coordinate(lat, lon);
			}
		}
		
		return null;
	}
}
