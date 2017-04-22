package com.assetmgmt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.assetmgmt.bean.Shop;
import com.assetmgmt.bean.ShopAddress;
import com.assetmgmt.geolocation.GeoCoordinates;

/**
 * @author rchugh
 */
@Service
public class ShopServiceImpl implements ShopService
{
	@Autowired
	private GeoCoordinates geoCoordinates;
	
	private static List<Shop> shopList = new ArrayList<Shop>();
	static
	{
		shopList =new ArrayList<>(Arrays.asList(new Shop("Store_CA",new ShopAddress(100,"1600 Amphitheatre Pkwy Mountain View", "CA",90001)),
									new Shop("Store_GA",new ShopAddress(101,"2300 Windy Ridge Pkwy SE", "GA",30339))));	
	}
	

	/**
     * Returns list of all the shops in the memory
     *
     * @return List<Shop> 
     */
	public List<Shop> getShops()
	{
		return shopList;
	}
	

	/**
     * Returns a particular shop after comparing the shop name;
     *
     * @param shopName
     * @return List<Shop> 
     */
	public Shop getShop(String shopName)
	{
		Shop shopObj= shopList.stream().filter(shop-> 
		shop.getShopName().equals(shopName)).findFirst().orElse(null);

		return shopObj;
	}
	
	/**
     * Add a shop if is a new one other wise it will override the old shop if it already exist
     * Also update the longitude and latitude present in shop address by calling google API and fetching them based on address field present in ShopAdress
     *
     * @param shop
     * @return Shop
     */
	public Shop addShop(Shop shop)
	{
		
		Optional<Shop> shopExist =	shopList.stream().filter(shopObj-> shopObj.getShopName().equals(shop.getShopName())).findAny();
		
		if(shopExist.isPresent())
		{
			shopList.remove(shopExist.get());
		}
		shopList.add(shop);
		
		geoCoordinates.getCoordinates(shop.getShopAddress());
	
		return shop;
	}
	
	/**
     * Returns the nearest shop based on longitude and latitude
     *
     * @param longitude
     * @param latitude
     * @return ShopAddress 
     */
	public ShopAddress getNearestShop(double longitude,double latitude)
	{
		ShopAddress closestStore = null;
		double distance=0;
		double temp=0;
		int count=0;
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
					count=1;;
				}
				if (temp < distance) 
				{
					distance = temp;
					closestStore = shop.getShopAddress();
				}		
			}	
		}
		return closestStore;
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
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters
		distance = Math.pow(distance, 2);

		return Math.sqrt(distance);
	}
}
