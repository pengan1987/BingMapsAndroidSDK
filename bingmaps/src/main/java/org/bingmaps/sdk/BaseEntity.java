package org.bingmaps.sdk;

import java.util.Hashtable;

/**
 * A base class that is inherited by all entity objects. 
 * This class is inherited by the Pushpin, Polyline, and Polygon classes.
 * @author rbrundritt
 */
public abstract class BaseEntity {
	/* Private Properties */
	
	private String _typeName;
	
	/* Constructor */
	
	/**
	 * Constructor
	 * @param typeName The type name of the entity class.
	 */
	public BaseEntity(String typeName){
		this.Metadata = new Hashtable<String, Object>();
		this._typeName = typeName;
	}

	/* Public Properties */
	
	/**
	 * The id of the entity object
	 */
	public int EntityId;
	
	/**
	 * The tilte of the object. Used in the Infobox.
	 */
	public String Title;
	
	/**
	 * A hashtable that stores the metadata of the object.
	 */
	public Hashtable<String, Object> Metadata;
	
	/**
	 * Returns the type name of the Entity object
	 * @return The type name of the Entity object
	 */
	public String getTypeName(){
		return _typeName;
	}

	/* Abstract Methods */
	
	public abstract String toString();
}
