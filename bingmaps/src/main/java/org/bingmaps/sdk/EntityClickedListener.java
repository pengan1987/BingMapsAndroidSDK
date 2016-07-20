package org.bingmaps.sdk;

/**
 * Listener for when an Entity object is clicked.
 * @author rbrundritt
 */
public interface EntityClickedListener {
	public abstract void onAvailableChecked(String layerName, int entityId);
}
