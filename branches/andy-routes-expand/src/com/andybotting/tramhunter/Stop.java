package com.andybotting.tramhunter;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.maps.GeoPoint;

import android.location.Location;


public class Stop { 
	
	private int id;
	private int tramTrackerID;
	private String flagStopNumber;
	private String primaryName;
	private String secondaryName;
	private String cityDirection;
	private Location location;
	private double latitude;
	private double longitude;
	private String suburb;
	private boolean starred = false;
	private Vector<Route> routes;

	
	
	private static final String NAME_PATTERN =
		"(.+) & (.+)";
	

	public void setId(int _id) {
		id = _id;
	}
	
	
	/**
	 * @return The id
	 */
	public int getId() {
		return id;
	}
	
	
	public void setTramTrackerID(int _tramTrackerID) {
		tramTrackerID = _tramTrackerID;
	}
	
	
	public int getTramTrackerID() {
		return tramTrackerID;
	} 
	

	public void setFlagStopNumber(String _flagStopNumber) {
		flagStopNumber = _flagStopNumber;
	}
	
	
	public String getFlagStopNumber() {
		return flagStopNumber;
	}	

	
	public void setStopName(String _stopName) {
		
		Pattern pattern = Pattern.compile(NAME_PATTERN);
		Matcher matcher = pattern.matcher(_stopName);
		
		if (matcher.find()) {
			primaryName = matcher.group(1);
			secondaryName = matcher.group(2);
		}
		
	}

	
	public String getStopName() {
	   String stopName = primaryName;
	   
	   if (secondaryName.length() > 0) {
		   stopName += " & " + secondaryName;
	   }
	   
	   return stopName;
	}	

	
	public void setPrimaryName(String _primaryName) {
			primaryName = _primaryName;
	}
	
	
	public String getPrimaryName() {
			return primaryName;
	}
		


	public void setSecondaryName(String _secondaryName) {
			secondaryName = _secondaryName;
	}
	
	
	public String getSecondaryName() {
		return secondaryName;
	}
	
   
	
	public void setCityDirection(String _cityDirection) {
		   cityDirection = _cityDirection;
	}
	
	
	public String getCityDirection() {
		  return cityDirection;
	}	
	
	
	public double getLatitude() {
		return latitude;
	}	

	
	public void setLatitude(Float _latitude) {
		latitude = _latitude;		
	}
	
	
	public double getLongitude() {
		return longitude;
	}	 
	
	public void setLongitude(Float _longitude) {
		longitude = _longitude;
		
	}
	
	
	
	public void setLocation(Location _location) {
		 location = _location;
	}
	
	
	public Location getLocation() {
		if (location != null) {
			return location;
		}
		else {
			location = new Location("dummy");
			location.setLongitude(longitude);
			location.setLatitude(latitude);
			return location;

		}
	}	
	
	
	/**
	 * @return a float representing the distance between the stop and
	 * a given location
	 */
	public double distanceTo(Location location) {
		double distance = this.getLocation().distanceTo(location);
		return distance;
	}
	
	
    public String formatDistanceTo(Location location){
    	
    	double distance = this.distanceTo(location);
    	
    	String result = "0m";
    	
    	if(distance > 10000) {
    		// More than 10kms
    		distance = distance / 1000;
    		result = (int)roundToDecimals(distance, 0) + "km";
    	}
    	else if(distance > 700) {
    		distance = distance / 1000;
    		result = roundToDecimals(distance, 1) + "km";
    	}
    	else {
    		result = (int)roundToDecimals(distance, 0) + "m";
    	}
    	
    	return result;
    }
    
    private static double roundToDecimals(double value, int decimalPlaces) {
    	int intValue = (int)((value * Math.pow(10, decimalPlaces)));
    	return (((double)intValue) / Math.pow(10, decimalPlaces));
    }

	
	/**
	 * @return the GeoPoint of the stop
	 */
	public GeoPoint getGeoPoint() {
        int lat1E6 = (int) (latitude * 1E6);
        int lng1E6 = (int) (longitude * 1E6);
        GeoPoint point = new GeoPoint(lat1E6, lng1E6);
        return point;
	}
	
	
	public void setSuburb(String _suburb) {
		suburb = _suburb;
	}
	
	
	public String getSuburb() {
	   return suburb;
	}   

	
	/**
	 * @return true if starred
	 */
	public boolean isStarred() {
		return starred;
	}

	
	/**
	 * @param starred true to star
	 */
	public void setStarred(boolean _starred) {
		starred = _starred;
	}
	
	/**
	 * @param starred true to star
	 */
	public void setStarred(int _starred) {
		if (_starred == 1) {
			starred = true;
		}
	}
	
	
	public void toggleStarred() {
		if(starred) {
			starred = false;
		}
		else {
			starred = true;
		}
	}
	
	
	public void setRoutes(Vector<Route> _routes) {
		routes = _routes;
	}
	
	
	public Vector<Route> getRoutes() {
	   return routes;
	}   	
	
	
	public String getRoutesString() {
		String routesString = "";
		
		if(routes.size() < 2) {
			routesString = "Route ";
		}
		else {
			routesString = "Routes ";
		}
		
		for(int i=0; i < routes.size(); i++) {
			Route route = routes.get(i);
			routesString += route.getNumber();
		
			if (i < routes.size() -2) {
				routesString += ", ";
			}
			else if (i == routes.size() -2){
				routesString += " and ";
			}
		}

		return routesString;
	}
	                               
	
	/**
	 * @return String representing the stop
	 */
	public String toString() {
		return ("Stop " + tramTrackerID + ": " + getPrimaryName());
	}
	
}