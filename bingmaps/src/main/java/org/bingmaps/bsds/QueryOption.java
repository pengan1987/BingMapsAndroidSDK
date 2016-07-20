package org.bingmaps.bsds;

import org.bingmaps.sdk.Utilities;

public class QueryOption {
	public String Filters;
	
	public String Format = "json";
	
	public String OrderBy;
	
	public String Select;
	
	public int Skip;
	
	public int Top;
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		if(!Utilities.isNullOrEmpty(Select)){
			sb.append("&$select=");
			sb.append(Select);
		}else{
			sb.append("&$select=*");
		}
		
		if(!Utilities.isNullOrEmpty(Format)){
			sb.append("&$format=");
			sb.append(Format);
		}
		
		if(Skip > 0){
			sb.append("&$skip=");
			sb.append(Skip);
		}
		
		if(Top < 0){
			Top = 25;
		}else if(Top > 250){
			Top = 250;
		}
		
		sb.append("&$top=");
		sb.append(Top);
		
		if(!Utilities.isNullOrEmpty(OrderBy)){
			sb.append("&$orderby=");
			sb.append(OrderBy);
		}
		
		if(!Utilities.isNullOrEmpty(Filters)){
			sb.append("&$filter=");
			sb.append(Filters);
		}
		
		return sb.toString();
	}
}
