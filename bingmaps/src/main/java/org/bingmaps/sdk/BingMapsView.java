package org.bingmaps.sdk;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewConfiguration;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * This class uses a WebView to host the Bing Maps V7 AJAX control.
 * Note that in order to use this class the map.html and BingMapsAndroid.js
 * files should be in the assets folder of your project. A set of Java
 * commands have been created that wrap the JavaScript functionality.
 *
 * @author Ricky Brundritt
 */
public class BingMapsView extends WebView {

	/* Private Properties */

    private BingMapsJavaScriptInterface _JSInterface;

    private int mTouchSlop = 50;

    protected static float x1,
            x2,
            y1,
            y2,
            x1_pre,
            y1_pre,
            dist_delta = 0,
            dist_curr = -1,
            dist_pre = -1;

    private long mLastGestureTime;
    private boolean mDragging = false;

    private Coordinate _centerCoordinate = new Coordinate(0, 0);
    private int _zoomLevel = 1;
    private LocationRect _bounds = new LocationRect(90, 180, -90, -180);
    private LayerManager _layerManager;
    private MapMovedListener _onMapMoved;
    private MapLoadedListener _onMapLoaded;
    private EntityClickedListener _entityClicked;
    
	/* Constructor */

    public BingMapsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    public BingMapsView(Context context) {
        super(context);

        initialize();
    }

	/* Public Methods */

    /**
     * Returns the bounds of the map view.
     *
     * @returns A LocationRect of the bounds of the map view.
     */
    public LocationRect getBounds() {
        return _bounds;
    }

    /**
     * Get the center coordinate of the map
     *
     * @return The coordinate of the center of the map
     */
    public Coordinate getCenter() {
        return _centerCoordinate;
    }

    /**
     * Returns a reference to the layer manager used by the Bing Maps view.
     *
     * @return A reference to the layer manager used by the Bing Maps view
     */
    public LayerManager getLayerManager() {
        return _layerManager;
    }

    /**
     * Get the current zoom level of the map
     *
     * @return The zoom level of the map.
     */
    public int getZoomLevel() {
        return _zoomLevel;
    }

    /**
     * Injects JavaScript into the web view. Use this if custom
     * JavaScript commands need to be sent to web view.
     *
     * @param js JavaScript to inject into Web View
     */
    public void injectJavaScript(final String js) {
        Log.d("BingMapsViewJS", js);
        this.post(new Runnable() {
            @Override
            public void run() {
                BingMapsView.this.loadUrl("javascript:(function() { " + js + " })();");
            }
        });
    }

    /**
     * Load an instance of the Bing Maps control
     *
     * @param bingMapsKey Bing Maps Key needed to authenticate the map control
     * @param center      The initial coordinate to center the map on.
     * @param zoomLevel   The initial zoom level to set the map at. Ignored if center coordinate is not set.
     */
    public void loadMap(String bingMapsKey, Coordinate center, int zoomLevel) {
        String url = Constants.BingMapsURL + "?bingMapKey=" + bingMapsKey;

        if (center != null && (zoomLevel > 0 && zoomLevel < 20)) {
            url += "&lat=" + center.Latitude + "&lon=" + center.Longitude;
            url += "&zoom=" + zoomLevel;
        }

        this.loadUrl(url);
    }

    /**
     * Pans the map the specified number of pixels
     *
     * @param dx number of pixels to offset the map by horizontally
     * @param dy number of pixels to offset the map by vertically
     */
    public void pan(int dx, int dy) {
        injectJavaScript("BingMapsAndroid.Pan(" + (int) dx + "," + (int) dy + ");");
    }

    /**
     * Set the heading of the map.
     *
     * @param heading The directional heading of the map. The heading is represented
     *                in geometric degrees with 0 or 360 = North, 90 = East, 180 = South, and 270 = West.
     */
    public void setHeading(double heading) {
        injectJavaScript("BingMapsAndroid.SetHeading(" + heading + ");");
    }

    /**
     * Sets the Entity clicked listener for the Bing Maps View.
     *
     * @param listener A EntityClickedListener that is to be set for the Bing Maps View.
     */
    public void setEntityClickedListener(EntityClickedListener listener) {
        this._entityClicked = listener;
    }

    /**
     * Sets the Map moved listener for the Bing Maps View.
     *
     * @param listener A MapMovedListener that is to be set for the Bing Maps View.
     */
    public void setMapMovedListener(MapMovedListener listener) {
        this._onMapMoved = listener;
    }

    /**
     * Sets the Map loaded listener for the Bing Maps View.
     *
     * @param listener A MapLoadedListener that is to be set for the Bing Maps View.
     */
    public void setMapLoadedListener(MapLoadedListener listener) {
        this._onMapLoaded = listener;
    }

    /**
     * Sets the view of the map so that it is centered and zoomed at the specified location.
     *
     * @param center Center coordinate to place the map at.
     * @param zoom   The zoom level at which to zoom the map to.
     */
    public void setCenterAndZoom(Coordinate center, int zoom) {
        injectJavaScript("BingMapsAndroid.SetCenterAndZoom(" + center.toString() + "," + zoom + ");");
    }

    /**
     * Sets the map view using the specified LocationRect bounds.
     *
     * @param bounds A LocationRect that represents the bounds of the view.
     */
    public void setMapView(LocationRect bounds) {
        injectJavaScript("BingMapsAndroid.SetMapView(" + bounds.toString() + ");");
    }

    /**
     * Sets the style of the map.
     *
     * @param mapStyle Map style to change the map to.
     */
    public void setMapStyle(String mapStyle) {
        injectJavaScript("BingMapsAndroid.SetMapStyle(" + mapStyle + ");");
    }

    /**
     * Zoom the map in
     */
    @Override
    public boolean zoomIn() {
        injectJavaScript("BingMapsAndroid.ZoomIn();");
        return true;
    }

    /**
     * Zoom the map out
     */
    @Override
    public boolean zoomOut() {
        injectJavaScript("BingMapsAndroid.ZoomOut();");
        return true;
    }

    public void overrideCulture(String mkt) {
        injectJavaScript("BingMapsAndroid.OverrideCulture('" + mkt + "');");
    }


	/* Private Methods */

    /*
     * Initializes the BingMapsView control
     */
    private void initialize() {
        setWillNotDraw(false);

        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);

        this.getSettings().setSupportMultipleWindows(false);
        this.getSettings().setSupportZoom(false);
        this.getSettings().setUseWideViewPort(true);
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);

        _JSInterface = new BingMapsJavaScriptInterface();
        this.addJavascriptInterface(_JSInterface, "BingMapsInterlop");

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        _layerManager = new LayerManager(this);
    }


	/* Private internal classes */

    final class BingMapsJavaScriptInterface {
        BingMapsJavaScriptInterface() {
        }

        @JavascriptInterface
        public void mapLoaded() {
            if (_onMapLoaded != null) {
                _onMapLoaded.onAvailableChecked();
            }
        }

        @JavascriptInterface
        public void mapMovedEvent(double lat, double lon, int zoomLevel, double north, double east, double south, double west) {
            _centerCoordinate = new Coordinate(lat, lon);
            _zoomLevel = zoomLevel;
            _bounds = new LocationRect(north, east, south, west);

            if (_onMapMoved != null) {
                _onMapMoved.onAvailableChecked();
            }
        }

        @JavascriptInterface
        public void entityClicked(String layerName, int entityId) {
            if (_entityClicked != null) {
                _entityClicked.onAvailableChecked(layerName, entityId);
            }
        }
    }
}

