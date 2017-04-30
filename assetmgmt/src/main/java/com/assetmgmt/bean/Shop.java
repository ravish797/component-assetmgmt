package com.assetmgmt.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author rchugh
 */
@XmlRootElement
@Entity
public class Shop 
{
	@Id
	private String shopName;
	
	@OneToOne(cascade = CascadeType.ALL)
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
