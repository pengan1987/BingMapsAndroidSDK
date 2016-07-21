package org.bingmaps.sdk;

import android.text.TextUtils;

import java.util.Locale;

public class TileLayer extends BaseLayer {
    /* Private Properties */

    private String _tileSourceURL;
    private double _opacity = 0.7;

	/* Constructors */

    public TileLayer(String layerName) {
        super(layerName);
    }

    public TileLayer(String layerName, String tileSourceURL) {
        super(layerName);
        _tileSourceURL = tileSourceURL;
    }

    public TileLayer(String layerName, String tileSourceURL, double opacity) {
        super(layerName);
        _tileSourceURL = tileSourceURL;
        _opacity = opacity;
    }

	/* Public Properties */

    public String getTileSourceURL() {
        return _tileSourceURL;
    }

    public void setTileSourceURL(String tileSourceURL) {
        _tileSourceURL = tileSourceURL;
        updateLayer();
    }

    public double getOpacity() {
        return _opacity;
    }

    public void setOpacity(double opacity) {
        _opacity = (opacity > 1) ? 1 : ((opacity < 0) ? 0 : opacity);
        updateLayer();
    }

	/* Public Methods */

    @Override
    public String toString() {
        if (TextUtils.isEmpty(_tileSourceURL))
            return null;
        String mercatorFormat = "new MM.TileSource({uriConstructor:'%s'})";
        String mercator = String.format(mercatorFormat, _tileSourceURL);
        String entityFormat = "new MM.TileLayer({mercator:%s,opacity:%f})";
        String entityResult = String.format(Locale.US, entityFormat, mercator, _opacity);
        return String.format("{LayerName:\"%s\",Entities:%s}", this.getLayerName(), entityResult);
    }

    public String toStringLegacy() {
        if (_tileSourceURL != null && _tileSourceURL != "") {
            String p = "{LayerName:'";
            p += this.getLayerName();
            p += "',Entities:[{EntityID:-1,Entity:new MM.TileLayer({mercator:new MM.TileSource({uriConstructor:'";
            p += _tileSourceURL;
            p += "'}), opacity:";
            p += _opacity;
            return p + "})}]}";
        }

        return null;
    }
}
