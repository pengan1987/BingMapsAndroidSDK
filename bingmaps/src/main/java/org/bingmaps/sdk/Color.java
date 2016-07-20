package org.bingmaps.sdk;

/**
 * A class that is used to represent colors to be used in the Bing Maps View.
 * @author rbrundritt
 */
public class Color {
	/* Constructors */
	
	/**
	 * Constructor
	 */
	public Color(){	
	}
	
	/**
	 * Constructor
	 * @param r Red byte value
	 * @param g Green byte value
	 * @param b Blue byte value
	 */
	public Color(byte r, byte g, byte b){
		this.A = 1;
		this.R = r;
		this.G = g;
		this.B = b;
	}
	
	/**
	 * Constructor
	 * @param a Alpha transparency byte value
	 * @param r Red byte value
	 * @param g Green byte value
	 * @param b Blue byte value
	 */
	public Color(byte a, byte r, byte g, byte b){
		this.A = a;
		this.R = r;
		this.G = g;
		this.B = b;
	}
	
	/* Public Properties */
	
	/**
	 * Alpha (opacity) between 0 and 1
	 */
	public byte A;
	
	/**
	 * Red color - between 0 and 255
	 */
	public byte R;
	
	/**
	 * Green color - between 0 and 255
	 */
	public byte G;
	
	/**
	 * Blue color - between 0 and 255
	 */
	public byte B;
	
	/* Public Methods */
	
	/**
	 * Generates a JSON string representation of the color for use with Bing Maps.
	 */
	public String toString(){
		return String.format("new MM.Color(%s, %s, %s, %s)", A, R, G, B);
	}
}
