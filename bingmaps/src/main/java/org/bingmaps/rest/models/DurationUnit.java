package org.bingmaps.rest.models;

import org.bingmaps.sdk.Utilities;

public class DurationUnit {
	public final static int Second = 0;
	public final static int Minute = 1;
	public final static int Hour = 2;
	public final static int Day = 3;
	public final static int Week = 4;
	public final static int Month = 5;
	public final static int Year = 6;
	
	public static int parse(String durationUnit){
		if(!Utilities.isNullOrEmpty(durationUnit)){
			if(durationUnit.equalsIgnoreCase("Second")){
	        	return Second;
	        }else if(durationUnit.equalsIgnoreCase("Minute")){
	        	return Minute;
	        }else if(durationUnit.equalsIgnoreCase("Hour")){
	        	return Hour;
	        }else if(durationUnit.equalsIgnoreCase("Day")){
	        	return Day;
	        }else if(durationUnit.equalsIgnoreCase("Week")){
	        	return Week;
	        }else if(durationUnit.equalsIgnoreCase("Month")){
	        	return Month;
	        }else if(durationUnit.equalsIgnoreCase("Year")){
	        	return Year;
	        }
		}
		
		return Second;
	}
	
	public static String toString(int confidence){
		switch(confidence){
			case Minute:
				return "Minute";
			case Hour:
				return "Hour";
			case Day:
				return "Day";
			case Week:
				return "Week";
			case Month:
				return "Month";
			case Year:
				return "Year";
			case Second:
			default:
				return "Second";
		}
	}
}
