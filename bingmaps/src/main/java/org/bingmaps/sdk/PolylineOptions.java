package org.bingmaps.sdk;

/**
 * Represents the options for a polyline.
 * @author rbrundritt
 */
public class PolylineOptions {

	/**
	 * The color of the polyline.
	 */
	public Color StrokeColor;
	
	/**
	 * The thickness of the polyline. Defaults to Blue.
	 */
	public int StrokeThickness;

	/**
	 * Returns a JSON representation of the PolylineOption object.
	 * @return A JSON representation of the PolylineOption object.
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		if(StrokeColor != null){
			sb.append("strokeColor:");
			sb.append(StrokeColor.toString());
		}else{
			//default polyline color to blue.
			sb.append("strokeColor:new MM.Color(200,0,0,200)");
		}
		
		if(StrokeThickness > 0){
			sb.append(",strokeThickness:");
			sb.append(StrokeThickness);
		}
		
		sb.append("}");
		return sb.toString();
	}
}
