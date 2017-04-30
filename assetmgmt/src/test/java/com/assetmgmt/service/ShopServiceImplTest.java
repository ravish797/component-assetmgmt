package com.assetmgmt.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.assetmgmt.bean.Shop;
import com.assetmgmt.bean.ShopAddress;
import com.assetmgmt.exception.AssetMgmtException;
import com.assetmgmt.geolocation.GeoCoordinates;
import com.assetmgmt.repository.ShopRepository;
import com.assetmgmt.restresponse.RestApiResponse;

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
	
	@Mock
	RestApiResponse response;
	
	@Mock
	private ShopRepository shopRepository;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	private Shop shop;
	
	private List<Shop> shopList = new ArrayList<>();
	
	@Before
    public void setup()
    {
		shop = new Shop("Store_Test",new ShopAddress(101,"2300 Windy Ridge Pkwy SE", "GA",30339,Double.valueOf(99),Double.valueOf(199)));
		shopList.add(shop);
    }
	
	@Test
    public void testAddShop_WithNullShopName()
    {
		shop = new Shop(null,new ShopAddress(101,"2300 Windy Ridge Pkwy SE", "GA",30339));
        doNothing().when(geoCoordinates).getCoordinates(any(ShopAddress.class));
        thrown.expect(AssetMgmtException.class);
        shopService.addShop(shop);
        verify(geoCoordinates, times(0)).getCoordinates(any(ShopAddress.class));
    }
	
	@Test
    public void testAddShop_WithNewShopName()
    {
		shop = new Shop("Store_Test_New",new ShopAddress(101,"2300 Windy Ridge Pkwy SE", "CA",560066));
		doReturn(shopList).when(shopRepository).findAll();
        doNothing().when(geoCoordinates).getCoordinates(any(ShopAddress.class));
        doReturn(shop).when(shopRepository).save(any(Shop.class));
        shopService.addShop(shop);
        verify(shopRepository, times(1)).findAll();
        verify(geoCoordinates, times(1)).getCoordinates(any(ShopAddress.class));
        verify(shopRepository, times(1)).save(any(Shop.class));
    }
	
	@Test
    public void testGetNearestShop()
    {
    	doReturn(shopList).when(shopRepository).findAll();
        shopService.getNearestShop(shop.getShopAddress().getLongitude(),shop.getShopAddress().getLatitude());
        verify(shopService, times(1)).calculateDistance(any(Double.class), any(Double.class), any(Double.class), any(Double.class));
    }
	
}
