package com.assetmgmt.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author rchugh
 */
@XmlRootElement
public class Shop 
{
	private String shopName;
	
	private ShopAddress shopAddress;
	
	public Shop()
	{
		
	}
	
	public Shop(String shopName, ShopAddress shopAddress) 
	{
		super();
		this.shopName = shopName;
		this.shopAddress = shopAddress;
	}
	
	public String getShopName() 
	{
		return shopName;
	}
	public void setShopName(String shopName) 
	{
		this.shopName = shopName;
	}
	public ShopAddress getShopAddress()
	{
		return shopAddress;
	}
	public void setShopAddress(ShopAddress shopAddress)
	{
		this.shopAddress = shopAddress;
	}
}
