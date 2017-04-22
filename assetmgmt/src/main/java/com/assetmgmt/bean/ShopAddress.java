package com.assetmgmt.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author rchugh
 */
@XmlRootElement
public class ShopAddress
{
	private int number;
	
	private int zipCode;
	
	private String address;

	private String state;
	
	private double latitude;
	
	private double longitude;
		
	public ShopAddress()
	{
		
	}
	
	public ShopAddress(int number,String address, String state, int zipCode) 
	{
		super();
		this.number = number;
		this.zipCode = zipCode;
		this.address = address;
		this.state = state;
	}
	
	public ShopAddress(int number, int zipCode, String address, String state, double latitude, double longitude)
	{
		super();
		this.number = number;
		this.zipCode = zipCode;
		this.address = address;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getNumber() 
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public int getZipCode() 
	{
		return zipCode;
	}

	public void setZipCode(int zipCode) 
	{
		this.zipCode = zipCode;
	}

	public String getAddress() 
	{
		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state) 
	{
		this.state = state;
	}
	
	
	public double getLatitude() 
	{
		return latitude;
	}

	public void setLatitude(double latitude) 
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude) 
	{
		this.longitude = longitude;
	}	
}
