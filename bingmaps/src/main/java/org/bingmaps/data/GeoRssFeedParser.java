package org.bingmaps.data;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class GeoRssFeedParser {
    // names of the XML tags
    public static final String PUB_DATE = "pubDate";
    public static final String DESCRIPTION = "description";
    public static final String LINK = "link";
    public static final String TITLE = "title";
    public static final String ITEM = "item";
    public static final String POINT = "point";
    public static final String POLYGON = "polygon";
    public static final String POLYLINE = "line";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "long";

    public List<GeoRssItem> parse(InputStream fileStream) {        
    	SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            GeoRssHandler handler = new GeoRssHandler();
            parser.parse(fileStream, handler);
            return handler.getItems();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
}
