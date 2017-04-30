package com.assetmgmt.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assetmgmt.bean.Shop;
import com.assetmgmt.restresponse.RestApiResponse;
import com.assetmgmt.service.ShopService;

/**
 * @author rchugh
 */
@RestController
@RequestMapping(value="/api/assetmgmt")
public class ShopController
{
	@Autowired
	private ShopService shopService;
	
	/**
     * This API will add the shop
     *
     * @param shop
     * @return Shop
     */
	@RequestMapping(value="/shop",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public RestApiResponse addShop(@RequestBody Shop shop)
	{
		return shopService.addShop(shop);
	}
	
	/**
     * This API will return list of all the shops
     *
     * @return List<Shop> 
     */
	@RequestMapping(value="/shop",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Shop> listShops()
	{
		return shopService.getShops();
	}
	
	/**
     * This API will return the particular shop based on shop name 
     *
     * @param shopName
     * @return Shop
     */
	@RequestMapping(value="/shop/{shopName}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public RestApiResponse getByShopId(@PathVariable String shopName)
	{
		return shopService.getShop(shopName);
	}
	
	/**
     * This API will return the nearest shop for the give longitude and latitude
     *
     * @param longitude
     * @param latitude
     * @return ShopAddress
     */
	@RequestMapping(value="/shopAddress/longitude/{longitude}/latitude/{latitude}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public RestApiResponse getNearestShop(@PathVariable String longitude,@PathVariable String latitude)
	{
		return shopService.getNearestShop(Double.parseDouble(longitude), Double.parseDouble(latitude));
	}
	
	
}
