package org.bingmaps.app;

public class Constants {
	public static final String BingMapsKey = "AkrQHLDk6bferFwpfke6DXGBz9fC1Jyog-jiBBwWblAmcwfQHPQqMt1TcgALnAYY";
	public static final String BingSpatialQueryKey = "Your Bing Spatial Query Key";
	
	public static final String BingSpatialAccessId = "20181f26d9e94c81acdf9496133d4f23";
	public static final String BingSpatialDataSourceName = "FourthCoffeeSample";
	public static final String BingSpatialEntityTypeName = "FourthCoffeeShops";

	public static final int DefaultSearchZoomLevel = 14;
	public static final int DefaultGPSZoomLevel = 15;
	
	//Search radius used for nearby search example
	public static final double SearchRadiusKM = 10;
	
	//Minimum distance a user must move in meters before GPS location updates on map
	public static final int GPSDistanceDelta = 0;
	
	//Minimum time that must past in ms before GPS will update users location
	public static final int GPSTimeDelta = 0;
	
	//Amount of time to display splash screen as map loads in seconds.
	public static final int SplashDisplayTime = 3000; 
	
	public class PanelIds{
		public static final int Splash = 0;
		public static final int About = 1;
		public static final int Map = 2;
	}
	
	public class PushpinIcons{
		public static final String Start = "file:///android_asset/startPin.png";
		public static final String End = "file:///android_asset/endPin.png";
		public static final String GPS = "file:///android_asset/pin_gps.png";
		public static final String RedFlag = "file:///android_asset/pin_red_flag.png";
	}
	
	public class DataLayers{
		public static final String Route = "route";
		public static final String GPS = "gps";
		public static final String Search = "search";
	}
}
