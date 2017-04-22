package com.assetmgmt.geolocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assetmgmt.bean.ShopAddress;
import com.assetmgmt.client.RestClient;

/**
 * @author rchugh
 */
@Service
public class GeoCoordinates
{
	@Autowired
	private RestClient restClient;
	
	private final String key= "&key=AIzaSyBbGu6FA3MfUidBBoJBE2_j8uquQlV-Bjw";
	
	private HttpURLConnection conn = null;
	
	/**
     * Gets longitude and latitude from google api based on provided address and updates the longitude and latitude defined on ShopAddress for a particular shop
     *
     * @param shopAddress
     */
	public void getCoordinates(ShopAddress shopAddress) {

		StringBuilder sb = new StringBuilder();
		String output;
		JSONObject jObject;
		String[] address = shopAddress.getAddress().split(" ");
		try 
		{
			StringBuilder completeUrl = new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?address=");
			
			for(int i=0;i<address.length;i++)
			{
				completeUrl.append(address[i]);
				completeUrl.append("+");
			}	
			completeUrl.append(shopAddress.getState());
			completeUrl.append(key);
			conn = restClient.getRestConnection(completeUrl.toString());

			if (conn.getResponseCode() != 200) 
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		
			while ((output = br.readLine()) != null) 
			{
				sb.append(output);
			}
			try 
			{
				jObject = new JSONObject(sb.toString());
				JSONArray resultArr = jObject.getJSONArray("results");
				JSONObject addressComponent = resultArr.getJSONObject(0);

				JSONObject geometry = addressComponent.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");

				Double latitude = location.getDouble("lat");
				Double longitude = location.getDouble("lng");

				if (latitude != null && longitude != null) 
				{
					shopAddress.setLatitude(latitude);
					shopAddress.setLongitude(longitude);
				}

			} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}
		catch (IOException ie) 
		{
			ie.printStackTrace();

		} 
		finally 
		{
			conn.disconnect();
		}
	}
	
}
