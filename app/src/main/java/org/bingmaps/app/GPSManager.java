package org.bingmaps.app;

import org.bingmaps.sdk.Coordinate;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

public class GPSManager {
    private LocationManager _locationManager;
    private String _bestProvider;
    private Activity _activity;
    private LocationListener _listener;

    public GPSManager(Activity activity, LocationListener listener) {
        _activity = activity;
        _listener = listener;
        _locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (_bestProvider == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            _bestProvider = _locationManager.getBestProvider(criteria, false);
        }

        if (_bestProvider != null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                _locationManager.requestLocationUpdates(_bestProvider, Constants.GPSTimeDelta, Constants.GPSDistanceDelta, listener);
            }
        }
    }

    public Coordinate GetCoordinate() {
        if (_bestProvider != null) {
            if (ActivityCompat.checkSelfPermission(_activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(_activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = _locationManager.getLastKnownLocation(_bestProvider);
                if (location != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    return new Coordinate(lat, lon);
                }
            }
        }
        return null;
    }

    public void refresh() {
        if (_bestProvider == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            _bestProvider = _locationManager.getBestProvider(criteria, false);
        }
        if (_bestProvider != null) {
            if (ActivityCompat.checkSelfPermission(_activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(_activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                _locationManager.requestLocationUpdates(_bestProvider, Constants.GPSTimeDelta, Constants.GPSDistanceDelta, _listener);
            }
        }
    }
}
