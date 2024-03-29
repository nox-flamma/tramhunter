/*  
 * Copyright 2010 Andy Botting <andy@andybotting.com>  
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This file is distributed in the hope that it will be useful, but  
 * WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU  
 * General Public License for more details.  
 *  
 * You should have received a copy of the GNU General Public License  
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.  
 *  
 * This file incorporates work covered by the following copyright and  
 * permission notice:
 * 
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andybotting.tramhunter.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.andybotting.tramhunter.TramHunterApplication;
import com.andybotting.tramhunter.objects.NextTram;
import com.andybotting.tramhunter.objects.Route;
import com.andybotting.tramhunter.objects.Stop;

public class TramTrackerServiceJSON implements TramTrackerService {

    private static final String TAG = "TTServiceJSON";
    private static final boolean LOGV = Log.isLoggable(TAG, Log.INFO);
	
	private static final String BASE_URL = "http://extranetdev.yarratrams.com.au/pidsservicejson/Controller";

	// Not until we use this interface properly 
	//private static final String CLIENTTYPE = "TRAMHUNTER";
	//private static final String CLIENTVERSION = "0.9.0";
	//private static final String CLIENTWEBSERVICESVERSION = "6.4.0.0";
	
	private Context mContext;
	
	public TramTrackerServiceJSON() {
		mContext = TramHunterApplication.getContext();
	}
	
	/**
	 * Fetch JSON data over HTTP
	 * @throws TramTrackerServiceException 
	 */
	public InputStream getJSONData(String url) throws TramTrackerServiceException{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        
        // Set the user agent
        String packageName = "Unknown";
        String packageVersion = "Unknown";
        
		try {
			packageName = mContext.getPackageName();
			PackageInfo pi = mContext.getPackageManager().getPackageInfo(packageName, 0);
			packageVersion = pi.versionName;
		} 
		catch (NameNotFoundException e) {
			// Nope
		}

        httpClient.getParams().setParameter("http.useragent", packageName + " " + packageVersion);
        
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } 
        catch (Exception e) {
        	throw new TramTrackerServiceException(e);
        }
       

        return data;
    }
	
	
    /**
     * Parse the given {@link InputStream} into a {@link JSONObject}.
     * @throws TramTrackerServiceException 
     */
    private static JSONObject parseJSONStream(InputStream is) throws IOException, JSONException, TramTrackerServiceException {
    	JSONObject jsonObject = null;
    	try {
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    		StringBuilder sb = new StringBuilder();
    		String line = null;
    		while ((line = reader.readLine()) != null) {
    			sb.append(line);
    		}
    		is.close();
    		String jsonData = sb.toString();
            if (LOGV) Log.v(TAG, "JSON Response: " + jsonData);
            jsonObject = new JSONObject(jsonData);
    	} 
    	catch(Exception e){
    		throw new TramTrackerServiceException(e);
    	}
		return jsonObject;
    }
    
    
    private static JSONObject getResponseObject(InputStream is) throws IOException, JSONException, TramTrackerServiceException {
    	
    	JSONObject responseObject = null;
    	JSONObject serviceData = parseJSONStream(is);
    	
        if (serviceData.getBoolean("isError"))
        	throw new TramTrackerServiceException("TramTracker Service Error");
        else
        	responseObject = serviceData.getJSONObject("responseObject");
        
        return responseObject;
    }

    
    private static JSONArray getResponseArray(InputStream is) throws IOException, JSONException, TramTrackerServiceException {
    	
    	JSONArray responseArray = null;
    	JSONObject serviceData = parseJSONStream(is);
    	
        if (serviceData.getBoolean("isError"))
        	throw new TramTrackerServiceException("TramTracker Service Error");
        else
        	 responseArray = serviceData.getJSONArray("responseObject");
        
        return responseArray;
    }
    
    
    /**
     * Parse the given {@link InputStream} into {@link Stop}
     * assuming a JSON format.
     * @return Stop
     */
    public static Stop parseStopInformation(JSONObject responseObject) throws TramTrackerServiceException {
		//	{
		//	   "responseObject":[
		//	      {
		//	         "FlagStopNo":"14",
		//	         "StopName":"Royal Melbourne Hospital & Flemington Rd",
		//	         "CityDirection":"towards City",
		//	         "Latitude":-37.799511541211,
		//	         "Longitude":144.95492172036,
		//	         "SuburbName":"Parkville",
		//	         "IsCityStop":false,
		//	         "HasConnectingBuses":true,
		//	         "HasConnectingTrains":false,
		//	         "HasConnectingTrams":false,
		//	         "StopLength":31,
		//	         "IsPlatformStop":true,
		//	         "Zones":"0,1"
		//	      }
		//	   ],
		//	   "isError":false
		//	}
    	
    	try {
	        Stop stop = new Stop();

	        String flagStopNo = responseObject.getString("FlagStopNo");
	        String stopName = responseObject.getString("StopName");
	        String cityDirection = responseObject.getString("CityDirection");
	        String suburbName = responseObject.getString("SuburbName");
	
			stop.setFlagStopNumber(flagStopNo);
			stop.setStopName(stopName);
			stop.setCityDirection(cityDirection);
			stop.setSuburb(suburbName);
	        
	    	return stop;
    	}
		catch (Exception e) {
			throw new TramTrackerServiceException(e);
		}    	
    }
	
    
	/**
	 * Get tram stop information for a given TramTracker ID
	 */
	public Stop getStopInformation(int tramTrackerID) throws TramTrackerServiceException {
		try {
			Stop stop = null;
			String url = BASE_URL + "/GetStopInformation.aspx?s=" + tramTrackerID;
			InputStream jsonData = getJSONData(url);
			JSONObject responseObject = getResponseObject(jsonData);
			stop = parseStopInformation(responseObject);
			stop.setTramTrackerID(tramTrackerID);
			return stop;
		} 
		catch (Exception e) {
			// Throw a TramTrackerServiceException to encapsulate all
			// other exceptions
			throw new TramTrackerServiceException(e);
		}
	}
	
	

    /**
     * Parse the given {@link InputStream} into {@link Stop}
     * assuming a JSON format.
     * @return Stop
     */
    public static List<NextTram> parseNextPredictedRoutesCollection(JSONArray responseArray) throws TramTrackerServiceException {

		//	[{
		//	    "responseObject": [
		//	        {
		//	            "TripID": null,
		//	            "InternalRouteNo": 55,
		//	            "RouteNo": "55",
		//	            "HeadboardRouteNo": "55",
		//	            "VehicleNo": 2003,
		//	            "Destination": "Domain Interchange",
		//	            "HasDisruption": false,
		//	            "IsTTAvailable": true,
		//	            "IsLowFloorTram": false,
		//	            "AirConditioned": true,
		//	            "DisplayAC": false,
		//	            "HasSpecialEvent": false,
		//	            "SpecialEventMessage": "Bus replacement Rte 59 btw Stop 57 Hawker St & Stop 59 Airport West til approx 7am Mon.",
		//	            "PredictedArrivalDateTime": "\/Date(1305382369000+1000)\/",
		//	            "RequestDateTime": "\/Date(1305381485739+1000)\/"
		//	        },
		//	        {
		//	            "TripID": null,
		//	            "InternalRouteNo": 59,
		//	            "RouteNo": "59",
		//	            "HeadboardRouteNo": "59",
		//	            "VehicleNo": 2025,
		//	            "Destination": "Flinders St City",
		//	            "HasDisruption": false,
		//	            "IsTTAvailable": true,
		//	            "IsLowFloorTram": false,
		//	            "AirConditioned": true,
		//	            "DisplayAC": false,
		//	            "HasSpecialEvent": true,
		//	            "SpecialEventMessage": "Bus replacement Rte 59 btw Stop 57 Hawker St & Stop 59 Airport West til approx 7am Mon.",
		//	            "PredictedArrivalDateTime": "\/Date(1305384840000+1000)\/",
		//	            "RequestDateTime": "\/Date(1305381485739+1000)\/"
		//	        }
		//	    ],
		//	    "isError": false
		//	}]      	
    	
    	
    	try {
	    	List<NextTram> nextTrams = new ArrayList<NextTram>();
	        
	        int responseObjectCount = responseArray.length();
	        for (int i = 0; i < responseObjectCount; i++) {
	        	
	        	JSONObject responseObject = responseArray.getJSONObject(i);

	            int internalRouteNo = responseObject.getInt("InternalRouteNo");
	            String routeNo = responseObject.getString("RouteNo");
	            String headboardRouteNo = responseObject.getString("HeadboardRouteNo");
	            int vehicleNo = responseObject.getInt("VehicleNo");
	            String destination = responseObject.getString("Destination");
	            boolean hasDisruption = responseObject.getBoolean("HasDisruption");
	            boolean isTTAvailable = responseObject.getBoolean("IsTTAvailable");
	            boolean isLowFloorTram = responseObject.getBoolean("IsLowFloorTram");
	            boolean airConditioned = responseObject.getBoolean("AirConditioned");
	            boolean displayAC = responseObject.getBoolean("DisplayAC");
	            boolean hasSpecialEvent = responseObject.getBoolean("HasSpecialEvent");
	            String specialEventMessage = responseObject.getString("SpecialEventMessage");
	            // Parse dates
	        	String predictedArrivalDateTimeString = responseObject.getString("PredictedArrivalDateTime");
	            Date predictedArrivalDateTime = parseTimestamp(predictedArrivalDateTimeString);
	            String requestDateTimeString = responseObject.getString("RequestDateTime");
	            Date requestDateTime = parseTimestamp(requestDateTimeString);
	
	            NextTram tram = new NextTram();
	            
				tram.setInternalRouteNo(internalRouteNo);
				tram.setRouteNo(routeNo);
				tram.setHeadboardRouteNo(headboardRouteNo);
				tram.setVehicleNo(vehicleNo);
				tram.setDestination(destination);
				tram.setHasDisruption(hasDisruption);
				tram.setIsTTAvailable(isTTAvailable);
				tram.setIsLowFloorTram(isLowFloorTram);
				tram.setAirConditioned(airConditioned);
				tram.setDisplayAC(displayAC);
				tram.setHasSpecialEvent(hasSpecialEvent);
				tram.setSpecialEventMessage(specialEventMessage);
				tram.setPredictedArrivalDateTime(predictedArrivalDateTime);
				tram.setRequestDateTime(requestDateTime);
				
				nextTrams.add(tram);
	        }
	        
			return nextTrams;
		} 
    	catch (Exception e) {
			throw new TramTrackerServiceException(e);
		}
    	
    }
	
    /**
     * Get the list of next trams for a given stop and route
     */
	public List<NextTram> getNextPredictedRoutesCollection (Stop stop, Route route) throws TramTrackerServiceException {

		try {
			List<NextTram> nextTrams = null;
		
			//  http://extranetdev.yarratrams.com.au/pidsservicejson/Controller/GetNextPredictedRoutesCollection.aspx?s=3173&r=0
			StringBuffer url = new StringBuffer();
			url.append(BASE_URL);
			url.append("/GetNextPredictedRoutesCollection.aspx");
			url.append("?s=" + stop.getTramTrackerID());
			
			// Filter the results by route
			if (route == null)
				url.append("&r=0");
			else
				url.append("&r=" + route.getNumber());
			
			InputStream jsonData = getJSONData(url.toString());
			JSONArray responseArray = getResponseArray(jsonData);
			nextTrams = parseNextPredictedRoutesCollection(responseArray);
			
			return nextTrams;
			
		} 
		catch (Exception e) {
			throw new TramTrackerServiceException(e);
		}
				
	}
	
	
	/**
	 * Convert the JSON timestamp string to a java date object
	 * @param timestamp
	 * @return date
	 */
	private static Date parseTimestamp(String timestamp) {
		//"PredictedArrivalDateTime": "\/Date(1305384840000+1000)\/",
		Long fixedDate = Long.parseLong(timestamp.substring(6, 19));
		Date date = new Date(fixedDate);
		return date;
	}
	

	public String getGUID() {
		return null;
	}


}
