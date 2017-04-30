package com.assetmgmt.geolocation;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.assetmgmt.bean.ShopAddress;
import com.assetmgmt.exception.AssetMgmtException;
import com.assetmgmt.rest.ShopController;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

/**
 * @author rchugh
 */
@Service
public class GeoCoordinates
{
	
	@Value("${google.maps.apiKey}")
	private String apiKey;
	
	private Logger logger = LoggerFactory.getLogger(GeoCoordinates.class);

	/**
     * Gets longitude and latitude from google api based on provided address and updates the longitude and latitude defined on ShopAddress for a particular shop
     *
     * @param shopAddress
     */
	public void getCoordinates(ShopAddress shopAddress) 
	{
		logger.trace("Start getCoordinates()");
		
		GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
		
		String address = shopAddress.getAddress()+", "+shopAddress.getState()+" "+shopAddress.getZipCode();

		logger.info("getCoordinates():: fetching coordinates for address:"+address);	
		try  
		{
			GeocodingResult results[] = GeocodingApi.geocode(context, address).await();
			for (GeocodingResult result : results) 
			{
				shopAddress.setLatitude(result.geometry.location.lat);
				shopAddress.setLongitude(result.geometry.location.lng);
				logger.info("getCoordinates():: Latitude:"+shopAddress.getLatitude()+"Longitude:"+shopAddress.getLongitude());	
			}
		} 
		catch (ApiException | InterruptedException | IOException e) 
		{
			throw new AssetMgmtException("Error occured while fetching cordinates::"+e.getMessage());
		}
		logger.trace("Exit getCoordinates()");	
	}
	
}
