package org.bingmaps.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a collection of Entity objects.
 */
public class EntityLayer extends BaseLayer {
	/* Private Properties */
	
	private int _idCounter;	
	private Map<String, HashMap<String, Object>> _metadata;
	private List<BaseEntity> _pendingEntities;
	
	/* Constructor */

	/**
	 * Constructor for the EntityLayer class which manages a collection of Entity objects
	 * @param layerName - The name of the layer in which this entity collection contains data for.
	 */
	public EntityLayer(String layerName){
		super(layerName);
		_idCounter = 0;
		_metadata = new HashMap<String, HashMap<String, Object>>();
		_pendingEntities = new ArrayList<BaseEntity>();
	}

	/* Public Methods */

	/**
	 * Adds an entity to the layer.
	 * @param object Entity to add to the layer.
	 */
	public void add(BaseEntity object){
		object.EntityId = _idCounter;
		_idCounter++;
		
		_pendingEntities.add(object);
	}

	/**
	 * Adds an entity to the layer and also keeps track of it's associated metadata.
	 * @param object Entity to add to the layer.
	 * @param metadata Metadata for Entity
	 */
	public void add(BaseEntity object, HashMap<String, Object> metadata) {
		object.EntityId = _idCounter;
		
		if(metadata != null){
			_metadata.put(_idCounter+"", metadata);
		}

		_idCounter++;

		_pendingEntities.add(object);
	}
	
	/**
	 * Clears all entities from the layer.
	 */
	public void clear() {
		_pendingEntities.clear();
		_metadata.clear();
		
		if(this.getBingMapsView() != null){
			this.getBingMapsView().getLayerManager().clearLayer(this.getLayerName());
		}
	}
	
	/**
	 * Gets metadata for an entity using it's id.
	 * @param id Id of the entity who's metadata is to be retrieved.
	 * @return An object that contains the metadata for an entity, or null.
	 */
	public HashMap<String, Object> getMetadataByEntityId(int id){
		String key = id+"";
		
		if(_metadata.containsKey(key)){
			return _metadata.get(key);
		}
		
		return null;
	}
	
	/**
	 * Inidicates if there are Entities that have been added to 
	 * the layer but have not been added to the map.
	 * @return A boolean indicating if there are Entities that have been added to 
	 * the layer but have not been added to the map.
	 */
	public boolean hasPendingEntites(){
		return _pendingEntities.size() > 0;
	}

	/**
	 * Returns a JSON representation of the Layer.
	 * @return A JSON representation of the Layer.
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		if(_pendingEntities.size() > 0){
			sb.append("{LayerName:'" + this.getLayerName() + "',Entities:[");
			
			int cnt = _pendingEntities.size();
			for(int i=0; i < cnt; i++){
				sb.append(_pendingEntities.get(i).toString());
				
				if(i < cnt - 1)
				{
					sb.append(",");
				}
			}
			
			sb.append("]}");
	
			_pendingEntities.clear();
		}
		return sb.toString();
	}
}
