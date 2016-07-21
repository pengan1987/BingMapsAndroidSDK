package org.bingmaps.sdk;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * This class manages layers that are added to a Bing Maps View.
 *
 * @author rbrundritt
 */
public class LayerManager {
    /* Private Properties */

    private BingMapsView _map;
    private Hashtable<String, BaseLayer> _layers;

	/* Constructor */

    /**
     * Constructor for the LayerManager class
     */
    public LayerManager(BingMapsView map) {
        _map = map;
        _layers = new Hashtable<String, BaseLayer>();
    }

	/* Public Methods */

    /**
     * Adds an object that inherits from the BaseLayer class to the map.
     * i.e. EntityLayer or TileLayer.
     *
     * @param layer - BaseLayer object
     */
    void addLayer(BaseLayer layer) {
        layer.setBingMapsView(_map);
        _layers.put(layer.getLayerName(), layer);
        _map.injectJavaScript("BingMapsAndroid.AddLayer(" + layer.toString() + ");");
    }

    public void addLayer(TileLayer layer) {
        layer.setBingMapsView(_map);
        _layers.put(layer.getLayerName(), layer);
        _map.injectJavaScript("BingMapsAndroid.InsertLayer(" + layer.toString() + ");");
    }

    /**
     * Adds an object that inherits from the BaseLayer class to the map.
     * i.e. EntityLayer or TileLayer.
     *
     * @param layer - BaseLayer object
     */
    public void addLayer(EntityLayer layer) {
        layer.setBingMapsView(_map);
        _layers.put(layer.getLayerName(), layer);
        _map.injectJavaScript("BingMapsAndroid.AddLayer(" + layer.toString() + ");");
    }

    /**
     * Deletes all shapes on a layer
     *
     * @param layerName - Name of the layer to clear.
     *                  If set to null all layers will be deleted from the map
     */
    public void clearLayer(String layerName) {
        layerName = (layerName == null) ? "" : layerName;
        _map.injectJavaScript("BingMapsAndroid.ClearLayer('" + layerName + "');");
    }

    /**
     * Get a Layer by it's name
     *
     * @param layerName - Name of the layer to get
     */
    public BaseLayer getLayerByName(String layerName) {
        if (_layers.containsKey(layerName)) {
            return _layers.get(layerName);
        }

        return null;
    }

    /**
     * Hides a Layer on the map
     *
     * @param layerName - Name of the layer on the map
     */
    public void hideLayer(String layerName) {
        layerName = (layerName == null) ? "" : layerName;
        _map.injectJavaScript("BingMapsAndroid.HideLayer('" + layerName + "');");
    }

    /**
     * Shows a Layer on the map
     *
     * @param layerName - Name of the layer on the map
     */
    public void showLayer(String layerName) {
        layerName = (layerName == null) ? "" : layerName;
        _map.injectJavaScript("BingMapsAndroid.ShowLayer('" + layerName + "');");
    }

    /**
     * Returns metadata for an Entity using the layer name and the id of the entity.
     *
     * @param layerName Name of the layer in which the entity is in.
     * @param id        The id of the entity object who's metadata is to be retrieved.
     * @return The metadata for an entity or null.
     */
    public HashMap<String, Object> GetMetadataByID(String layerName, int id) {
        BaseLayer layer = getLayerByName(layerName);

        if (layer.getClass() == EntityLayer.class) {
            EntityLayer eLayer = (EntityLayer) layer;
            return eLayer.getMetadataByEntityId(id);
        }

        return null;
    }
}
