package com.assetmgmt.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.assetmgmt.bean.Shop;
import com.assetmgmt.bean.ShopAddress;
import com.assetmgmt.geolocation.GeoCoordinates;

@RunWith(MockitoJUnitRunner.class)
public class ShopServiceImplTest
{
	@Spy
    @InjectMocks
	ShopServiceImpl shopService;
	
	@Mock
	ShopServiceImpl shopServiceObj;
	
	@Mock
	GeoCoordinates geoCoordinates;
	
	private Shop shop;
	
	@Before
    public void setup()
    {
		shop = new Shop("Store_Test",new ShopAddress(101,"2300 Windy Ridge Pkwy SE", "GA",30339)); 
    }
	
	@Test
    public void testAddShop()
    {
        doNothing().when(geoCoordinates).getCoordinates(any(ShopAddress.class));
        shopService.addShop(shop);
        verify(geoCoordinates, times(1)).getCoordinates(any(ShopAddress.class));
        shopService.getShops().remove(shop);
    }
	
	@Test
    public void testGetNearestShop()
    {
        shopService.getNearestShop(shop.getShopAddress().getLongitude(),shop.getShopAddress().getLatitude());
        verify(shopService, times(2)).calculateDistance(any(Double.class), any(Double.class), any(Double.class), any(Double.class));
    }
	
}
