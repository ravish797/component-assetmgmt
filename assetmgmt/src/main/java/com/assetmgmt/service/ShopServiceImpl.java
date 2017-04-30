package com.assetmgmt.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.assetmgmt.bean.Shop;
import com.assetmgmt.bean.ShopAddress;
import com.assetmgmt.constants.MessageConstants;
import com.assetmgmt.exception.AssetMgmtException;
import com.assetmgmt.geolocation.GeoCoordinates;
import com.assetmgmt.repository.ShopRepository;
import com.assetmgmt.rest.ShopController;
import com.assetmgmt.restresponse.RestApiResponse;

/**
 * @author rchugh
 */
@Service
public class ShopServiceImpl implements ShopService
{
	@Autowired
	private GeoCoordinates geoCoordinates;
	
	@Autowired
	private ShopRepository shopRepository;
	
	@Autowired
	private RestApiResponse response;
	
	private Logger logger = LoggerFactory.getLogger(ShopController.class);
	
	/**
     * Returns list of all the shops in the memory
     *
     * @return List<Shop> 
     */
	public List<Shop> getShops()
	{
		return shopRepository.findAll();
	}

	/**
     * Returns a particular shop after comparing the shop name;
     *
     * @param shopName
     * @return RestApiResponse 
     */
	public RestApiResponse getShop(String shopName)
	{
		logger.trace("Start getShop()");
		if(shopName==null)
		{
			throw new AssetMgmtException("Shop name is invalid");
		}
		response.clear();
		Shop shop=shopRepository.findOne(shopName);
		if(shop!=null)
		{
			response.setData(shop);
			response.setMessage(MessageConstants.SHOPFOUND);
		}
		else
		{
			response.setMessage(MessageConstants.NOSHOPFOUND);
		}
		logger.info("getShop()::Shop data "+response.getData());
		logger.trace("Exit getShop()");
		return response;
	}
	
	/**
     * Add a shop if is a new one other wise it will override the old shop if it already exist
     * Also update the longitude and latitude present in shop address by calling google API and fetching them based on address field present in ShopAdress
     *
     * @param shop
     * @return RestApiResponse
     */
	public RestApiResponse addShop(Shop shop)
	{
		logger.trace("Start addShop()");
		
		if(shop.getShopName()==null)
		{
			throw new AssetMgmtException("Shop name cannot be empty");
		}

		boolean check=false;
		List<Shop> shopList=shopRepository.findAll();
		if(!CollectionUtils.isEmpty(shopList))
		{
			Optional<Shop> shopExist =	shopList.stream().filter(shopObj-> shopObj.getShopName().equals(shop.getShopName())).findAny();
			
			if(shopExist.isPresent())
			{
				shopList.remove(shopExist.get());
				check=true;
			}	
		}
		shop.getShopAddress().setShopName(shop.getShopName());
		geoCoordinates.getCoordinates(shop.getShopAddress());
		shopRepository.save(shop);
		response.setData(shop);
		
		if(check)
		{
			response.setMessage(MessageConstants.SHOPREPLACED);	
			logger.info("addShop()::"+MessageConstants.SHOPREPLACED);		
		}
		else
		{
			response.setMessage(MessageConstants.SHOPADEDD);
			logger.info("addShop()::"+MessageConstants.SHOPADEDD);		
		}
	
		logger.trace("Exit addShop()");
		return response;
	}
	
	/**
     * Returns the nearest shop based on longitude and latitude
     *
     * @param longitude
     * @param latitude
     * @return RestApiResponse 
     */
	public RestApiResponse getNearestShop(double longitude,double latitude)
	{
		logger.trace("Start getNearestShop()");
		
		logger.debug("getNearestShop():: longitude: "+longitude+"latitude: "+latitude);
		ShopAddress closestStore = null;
		double distance=0;
		double temp=0;
		int count=0;
		response.clear();
		List<Shop> shopList=shopRepository.findAll();
		shopList = shopList.stream().filter(shop-> (shop.getShopAddress().getLongitude()!=0 && shop.getShopAddress().getLongitude()!=0)).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(shopList))
		{
			for(Shop shop:shopList)
			{
				temp=calculateDistance(longitude,shop.getShopAddress().getLongitude(),
						latitude,shop.getShopAddress().getLatitude());
				if(count==0)
				{
					distance = temp;
					closestStore=shop.getShopAddress();
					count=1;
				}
				if (temp < distance) 
				{
					distance = temp;
					closestStore = shop.getShopAddress();
				}		
			}	
		response.setData(closestStore);
		response.setMessage(MessageConstants.NEARESTLOCATION);
		logger.info("getNearestShop()::"+MessageConstants.NEARESTLOCATION);
		}
		else
		{
			response.setMessage(MessageConstants.NOSHOPFOUND);
			logger.info("getNearestShop()::"+MessageConstants.NOSHOPFOUND);
		}
		logger.trace("Exit getNearestShop()");
		return response;
	}
	
	/**
     * Calculates the distance based on longitude and latitude
     *
     * @param lon1
     * @param lon2
     * @param lat1
     * @param lat2
     * @return double 
     */
	public double calculateDistance(double lon1,double lon2, double lat1,double lat2)
	{
		logger.trace("Start calculateDistance()");
		
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters
		distance = Math.pow(distance, 2);
		
		logger.trace("Exit calculateDistance()");

		return Math.sqrt(distance);
	}
}
