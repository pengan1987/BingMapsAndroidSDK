package org.bingmaps.sdk;

/**
 * Base class used to represent a layer of data on the map. 
 * Both the EntityLayer and Tile Layer classes are derived from this class.
 * @author rbrundritt
 */
public abstract class BaseLayer {
	/* Private Properties */

	private String _layerName;
	private BingMapsView _map;

	/* Constructor */
	
	/**
	 * Base class for Shapes, Entities and Tiles 
	 * @param layerName - The name of the layer in which this entity collection contains data for.
	 */
	public BaseLayer(String layerName){
		_layerName = layerName;	
	}
	
	/* Public Properties */
	
	/**
	 * Gets the name of the layer. Each layer must have a unique name. 
	 * This is used as the uniue identifier for each layer. 
	 */
	public String getLayerName(){
		return _layerName;
	}
	
	/**
	 * Returns a reference the Bing Maps View object that this base layer has been added to.
	 * @return A reference the Bing Maps View object that this base layer has been added to.
	 */
	public BingMapsView getBingMapsView() {
		return _map;
	}

	/**
	 * Sets the Bing Maps View that the layer has been added to.
	 * @param bmView The Bing Maps View that the layer has been added to.
	 */
	public void setBingMapsView(BingMapsView bmView) {
		_map = bmView;
	}
	
	/* Public Methods */
	
	/**
	 * 
	 */
	public void updateLayer(){
		if(_map != null){
			_map.getLayerManager().addLayer(this);
		}
	}
	
	public void hideLayer() {
		_map.getLayerManager().hideLayer(this.getLayerName());
	}

	public void showLayer() {
		_map.getLayerManager().showLayer(this.getLayerName());
	}
}
