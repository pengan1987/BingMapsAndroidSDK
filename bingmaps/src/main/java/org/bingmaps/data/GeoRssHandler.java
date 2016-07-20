package org.bingmaps.data;

import java.util.ArrayList;
import java.util.List;

import org.bingmaps.sdk.Coordinate;
import org.bingmaps.sdk.Polygon;
import org.bingmaps.sdk.Polyline;
import org.bingmaps.sdk.Pushpin;
import org.bingmaps.sdk.Utilities;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GeoRssHandler extends DefaultHandler{
    private List<GeoRssItem> items;
    private GeoRssItem currentItem;
    private StringBuilder builder;
    private Double _lat = null, _lon = null;
    
    public List<GeoRssItem> getItems(){
        return this.items;
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);
        if (this.currentItem != null){
            if (localName.equalsIgnoreCase(GeoRssFeedParser.TITLE)){
            	currentItem.Title = builder.toString();
            }else if (localName.equalsIgnoreCase(GeoRssFeedParser.DESCRIPTION)){
            	currentItem.Description = builder.toString();
            }else if (localName.equalsIgnoreCase(GeoRssFeedParser.POINT)){
            	String[] p = builder.toString().replace("\n","").trim().split("\\s");
            	if(p.length >= 2){
            		currentItem.Entity = new Pushpin(new Coordinate(Double.parseDouble(p[0]), Double.parseDouble(p[1])));
            	}
            }else if (localName.equalsIgnoreCase(GeoRssFeedParser.POLYLINE)){
            	List<Coordinate> points = parseStringCoordinates(builder.toString());
            	
            	if(points.size() >= 2){
            		currentItem.Entity = new Polyline(points);
            	}
            }else if (localName.equalsIgnoreCase(GeoRssFeedParser.POLYGON)){
            	List<Coordinate> points = parseStringCoordinates(builder.toString());
            	
            	if(points.size() >= 3){
            		currentItem.Entity = new Polygon(points);
            	}
            }else if (localName.equalsIgnoreCase(GeoRssFeedParser.LATITUDE)){
        		_lat = Double.parseDouble(builder.toString());
            	
            	if(_lat != null && _lon != null){
            		currentItem.Entity = new Pushpin(new Coordinate(_lat, _lon));
            	}
            }else if (localName.equalsIgnoreCase(GeoRssFeedParser.LONGITUDE)){
            	_lon = Double.parseDouble(builder.toString());
            	
            	if(_lat != null && _lon != null){
            		currentItem.Entity = new Pushpin(new Coordinate(_lat, _lon));
            	}
            }else if (localName.equalsIgnoreCase(GeoRssFeedParser.ITEM)){
                items.add(currentItem);
            }  
        }
        builder.setLength(0);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        items = new ArrayList<GeoRssItem>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if (localName.equalsIgnoreCase(GeoRssFeedParser.ITEM)){
            this.currentItem = new GeoRssItem();
            _lat = null;
            _lon = null;
        }
    }
    
    /* Private Methods */
    
    private List<Coordinate> parseStringCoordinates(String sLocations)
    {
    	List<Coordinate> locations = new ArrayList<Coordinate>();

        if (!Utilities.isNullOrEmpty(sLocations))
        {
            String[] sCoords = sLocations.replace("\n","").trim().split("\\s");

            for (int i = 0; i < sCoords.length - 1; i = i + 2)
            {
                locations.add(new Coordinate(Double.parseDouble(sCoords[i]), Double.parseDouble(sCoords[i + 1])));
            }
        }

        return locations;
    }
}
