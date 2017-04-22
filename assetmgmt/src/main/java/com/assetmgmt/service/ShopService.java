package com.assetmgmt.service;

import java.util.List;
import com.assetmgmt.bean.Shop;
import com.assetmgmt.bean.ShopAddress;

/**
 * @author rchugh
 */
public interface ShopService
{
	public List<Shop> getShops();
	
	public Shop getShop(String shopName);
	
	public Shop addShop(Shop shop);
	
	public ShopAddress getNearestShop(double longitude,double latitude);

}
