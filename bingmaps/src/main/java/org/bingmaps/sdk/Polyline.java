package org.bingmaps.sdk;

import java.util.List;

public class Polyline extends BaseEntity {
	private String _locations;
	
	public Polyline(){
		super(EntityType.Polyline);
	}

	public Polyline(List<Coordinate> locations){
		super(EntityType.Polyline);
		_locations = Utilities.LocationsToString(locations);
	}
	
	public Polyline(String locations){
		super(EntityType.Polyline);
		_locations = locations;
	}
	
	public PolylineOptions Options;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{EntityId:");
		sb.append(EntityId);
		
		sb.append(",Entity:new MM.Polyline(");
		sb.append(_locations);
		
		if(Options != null){
			sb.append(",");
			sb.append(Options.toString());
		}
		sb.append(")");
		
		if(!Utilities.isNullOrEmpty(Title)){
			sb.append(",title:'");
			sb.append(Title);
			sb.append("'");
		}
		
		sb.append("}");
		
		return sb.toString();
	}
}
