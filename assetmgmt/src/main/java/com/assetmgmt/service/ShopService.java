package com.assetmgmt.service;

import java.util.List;
import com.assetmgmt.bean.Shop;
import com.assetmgmt.restresponse.RestApiResponse;

/**
 * @author rchugh
 */
public interface ShopService
{
	public List<Shop> getShops();
	
	public RestApiResponse getShop(String shopName);
	
	public RestApiResponse addShop(Shop shop);
	
	public RestApiResponse getNearestShop(double longitude,double latitude);

}
