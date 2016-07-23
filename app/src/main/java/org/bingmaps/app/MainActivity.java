package org.bingmaps.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ViewFlipper;

import org.bingmaps.sdk.BingMapsView;
import org.bingmaps.sdk.Coordinate;
import org.bingmaps.sdk.EntityClickedListener;
import org.bingmaps.sdk.EntityLayer;
import org.bingmaps.sdk.MapLoadedListener;
import org.bingmaps.sdk.MapMovedListener;
import org.bingmaps.sdk.MapStyles;
import org.bingmaps.sdk.Point;
import org.bingmaps.sdk.Polyline;
import org.bingmaps.sdk.PolylineOptions;
import org.bingmaps.sdk.Pushpin;
import org.bingmaps.sdk.PushpinOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BingMapsView bingMapsView;
    private GPSManager _GPSManager;
    private EntityLayer _gpsLayer;
    private ProgressDialog _loadingScreen;

    private Activity _baseActivity;

    CharSequence[] _dataLayers;
    boolean[] _dataLayerSelections;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // OPTION Lock map orientation
        // setRequestedOrientation(1);

        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Initialize();
    }

    private void Initialize() {
        _baseActivity = this;
        _GPSManager = new GPSManager(this, new GPSLocationListener());

        // Add more data layers here
        _dataLayers = new String[]{getString(R.string.traffic)};
        _dataLayerSelections = new boolean[_dataLayers.length];

        _loadingScreen = new ProgressDialog(this);
        _loadingScreen.setCancelable(false);
        _loadingScreen.setMessage(this.getString(R.string.loading) + "...");

        bingMapsView = (BingMapsView) findViewById(R.id.mapView);

        // Create handler to switch out of Splash screen mode
        final Handler viewHandler = new Handler() {
            public void handleMessage(Message msg) {
                ((ViewFlipper) findViewById(R.id.flipper)).setDisplayedChild(1);
            }
        };

        // Add a map loaded event handler
        bingMapsView.setMapLoadedListener(new MapLoadedListener() {
            public void onAvailableChecked() {
                // hide splash screen and go to map
                viewHandler.sendEmptyMessage(0);

                // Add GPS layer
                _gpsLayer = new EntityLayer(Constants.DataLayers.GPS);
                bingMapsView.getLayerManager().addLayer(_gpsLayer);
                UpdateGPSPin();
                updateMarker();
            }
        });

        // Add a entity clicked event handler
        bingMapsView.setEntityClickedListener(new EntityClickedListener() {
            public void onAvailableChecked(String layerName, int entityId) {
                HashMap<String, Object> metadata = bingMapsView
                        .getLayerManager().GetMetadataByID(layerName, entityId);
                DialogLauncher.LaunchEntityDetailsDialog(_baseActivity,
                        metadata);
            }
        });

        // Load the map
        bingMapsView.loadMap(Constants.BingMapsKey,
                _GPSManager.GetCoordinate(), Constants.DefaultGPSZoomLevel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int selectedId = item.getItemId();

        if (selectedId == R.id.zoomInBtn) {
            bingMapsView.zoomIn();
        }

        if (selectedId == R.id.zoomOutBtn) {
            bingMapsView.zoomOut();
        }

        // Map Mode menu items
        if (selectedId == R.id.roadBtn) {
            bingMapsView.setMapStyle(MapStyles.Road);
            item.setChecked(!item.isChecked());
            return true;
        }
        if (selectedId == R.id.aerialBtn) {
            bingMapsView.setMapStyle(MapStyles.Aerial);
            item.setChecked(!item.isChecked());
            return true;
        }
        if (selectedId == R.id.streetSideBtn) {
            bingMapsView.setMapStyle(MapStyles.StreetSide);
            item.setChecked(!item.isChecked());
            return true;
        }
        if (selectedId == R.id.ordnanceSurveyBtn) {
            bingMapsView.setMapStyle(MapStyles.OrdnanceSurvey);
            item.setChecked(!item.isChecked());
            return true;
        }
        // More option items
        if (selectedId == R.id.aboutMenuBtn) {
            DialogLauncher.LaunchAboutDialog(this);
            return true;
        }
        if (selectedId == R.id.layersMenuBtn) {
            DialogLauncher.LaunchLayersDialog(this, bingMapsView, _dataLayers,
                    _dataLayerSelections);
            return true;
        }
        if (selectedId == R.id.clearMapMenuBtn) {
            bingMapsView.getLayerManager().clearLayer(null);

            // unselect all layers
            for (int i = 0; i < _dataLayerSelections.length; i++) {
                _dataLayerSelections[i] = false;
            }

            // re-add GPS layer
            bingMapsView.getLayerManager().clearLayer(Constants.DataLayers.GPS);
            UpdateGPSPin();
            return true;
        }
        // GPS Menu Item
        if (selectedId == R.id.gpsMenuBtn) {
            Coordinate coord = _GPSManager.GetCoordinate();

            if (coord != null) {
                // Center on users GPS location
                bingMapsView.setCenterAndZoom(coord,
                        Constants.DefaultGPSZoomLevel);
            }
            return true;
        }
        // Search Menu Item
        if (selectedId == R.id.searchMenuBtn) {
            DialogLauncher.LaunchSearchDialog(this, bingMapsView,
                    loadingScreenHandler);
            return true;
        }
        // Directions Menu Item
        if (selectedId == R.id.directionsMenuBtn) {
            DialogLauncher.LaunchDirectionsDialog(this, bingMapsView,
                    loadingScreenHandler);
            return true;
        }

        if (selectedId == R.id.overrideCultureBtn) {
            DialogLauncher.LaunchOverrideCultureDialog(this, bingMapsView);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UpdateGPSPin() {
        PushpinOptions opt = new PushpinOptions();
        opt.Icon = Constants.PushpinIcons.GPS;
        Pushpin p = new Pushpin(_GPSManager.GetCoordinate(), opt);
        if (p.Location != null && _gpsLayer != null) {
            _gpsLayer.clear();
            _gpsLayer.add(p);
            _gpsLayer.updateLayer();
        }
    }

    public void updateMarker() {

        List<Coordinate> listCoord = new ArrayList<>();
        // EntityLayer is used for map overlay
        EntityLayer entityLayer = (EntityLayer) bingMapsView.getLayerManager()
                .getLayerByName(Constants.DataLayers.Search);
        if (entityLayer == null) {
            entityLayer = new EntityLayer(Constants.DataLayers.Search);
        }
        entityLayer.clear();

        double longitude = Double.parseDouble("-122.3");
        double latitude = Double.parseDouble("47.6");
        Coordinate location = new Coordinate(latitude, longitude);
        // Use Pushpin to mark on the map
        // PushpinOptions is used to set attributes for Pushpin
        // opt.Icon - The icon of PushPin, opt.Anchor - The position to display Pushpin
        PushpinOptions opt = new PushpinOptions();
        opt.Icon = Constants.PushpinIcons.RedFlag;
        opt.Width = 20;
        opt.Height = 35;
        opt.Anchor = new Point(11, 10);
        Pushpin p = new Pushpin(location, opt);
        if (p.Location != null) {
            listCoord.add(location);
            entityLayer.add(p);
        }

        // Add the entityLayer to mapView's LayerManager
        bingMapsView.getLayerManager().addLayer(entityLayer);
        entityLayer.updateLayer();

        // set the center location and zoom level of map
        bingMapsView.setCenterAndZoom(location, 11);

        // Polyline used to draw lines on the MapView
        // PolylineOptions have multiple attributes for the line
        // polylineOptions.StrokeThickness
        // polylineOptions.StrokeColor
        Polyline routeLine = new Polyline(listCoord);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.StrokeThickness = 3;
        routeLine.Options = polylineOptions;
        entityLayer.add(routeLine);

    }

    @SuppressWarnings("unused")
    private final MapMovedListener mapMovedListener = new MapMovedListener() {
        public void onAvailableChecked() {
            // OPTION Add logic to Update Layers here.
            // This will update data layers when the map is moved.
        }
    };

    /**
     * Handler for loading Screen
     */
    protected Handler loadingScreenHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0) {
                _loadingScreen.hide();
            } else {
                _loadingScreen.show();
            }
        }
    };

    public class GPSLocationListener implements LocationListener {
        public void onLocationChanged(Location arg0) {
            UpdateGPSPin();
        }

        public void onProviderDisabled(String arg0) {
        }

        public void onProviderEnabled(String arg0) {
        }

        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        }
    }
}