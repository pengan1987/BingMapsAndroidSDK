package org.bingmaps.sdk;

/**
 * Constant values to be used by the Bing Maps SDK.
 * @author rbrundritt
 */
public class Constants {
	/**
	 * Local URL to the web page that contains the Bing Maps wrapper JavaScript and HTML.
	 */
	public static final String BingMapsURL = "file:///android_asset/map.html";
	
	/**
	 * URL to the Bing Maps Traffic tile layer.
	 */
	public static final String TrafficTileLayerURI = "http://ecn.t{subdomain}.tiles.virtualearth.net/tiles/dp/content?p=tf&a={quadkey}";
	
	//Add Bing Maps REST Service URLS
	
	/**
	 * Earth radii constant values.
	 */
	public static class RadiusEarth{
		/**
		 * Radius of the earth in miles.
		 */
		public static final double Miles = 3963.189;
		
		/**
		 * Radius of earth in kilometers.
		 */
		public static final double KM = 6378.135;
	}
}