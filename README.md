# component-assetmgmt

Below are the details of the rest api exposed and the request and response for the same

Method GET
URL http://localhost:8080/api/assetmgmt/shop
Desc This api returns all the shop in from the memory 

Response
[
  {
    "shopName": "Store_GA1",
    "shopAddress": {
      "shopName": "Store_GA1",
      "number": 101,
      "zipCode": 90001,
      "address": "1600 Amphitheatre Pkwy Mountain View",
      "state": "GA",
      "latitude": 37.4223895,
      "longitude": -122.0843123
    }
  },
  {
    "shopName": "Store_GA2",
    "shopAddress": {
      "shopName": "Store_GA2",
      "number": 101,
      "zipCode": 3039,
      "address": "2300 Windy Ridge Pkwy SE",
      "state": "GA",
      "latitude": 33.9059944,
      "longitude": -84.460171
    }
  }
]

Method POST
URL http://localhost:8080/api/assetmgmt/shop
Desc This api add the shop into the list of the shops and override with new value if it already exists 
	 It also internally calls google api to fetch longitude and latitude which it update to shop address

Request
 {
  "shopName": "Store_GA1",
  "shopAddress": {
  	"shopName": "Store_GA1",
    "number": 101,
    "zipCode": 90001,
    "address": "1600 Amphitheatre Pkwy Mountain View",
    "state": "GA",
    "latitude": 0,
    "longitude": 0
  }
}
  
Response
{
  "message": "Shop added succesfully",
  "data": {
    "shopName": "Store_GA1",
    "shopAddress": {
      "shopName": "Store_GA1",
      "number": 101,
      "zipCode": 90001,
      "address": "1600 Amphitheatre Pkwy Mountain View",
      "state": "GA",
      "latitude": 37.4223895,
      "longitude": -122.0843123
    }
  },
  "error": null
}
	 

Method GET
URL http://localhost:8080/api/assetmgmt/shop/{shopName}
Sample : http://localhost:8080/api/assetmgmt/shop/Store_GA
Desc This api fetch the shop based on the shop name which is unique for every shop.

Response
{
  "message": "Shop found successfully",
  "data": {
    "shopName": "Store_GA1",
    "shopAddress": {
      "shopName": "Store_GA1",
      "number": 101,
      "zipCode": 90001,
      "address": "1600 Amphitheatre Pkwy Mountain View",
      "state": "GA",
      "latitude": 37.4223895,
      "longitude": -122.0843123
    }
  },
  "error": null
}

Method GET
URL http://localhost:8080/api/assetmgmt/shopAddress/longitude/{longitude}/latitude/{latitude}
Sample http://localhost:8080/api/assetmgmt/shopAddress/longitude/30/latitude/100
Desc This api provides the  nearest shop address based on the longitude and latitude provided as a input.

Response
{
  "message": "Nearest location is found",
  "data": {
    "shopName": "Store_GA1",
    "number": 101,
    "zipCode": 90001,
    "address": "1600 Amphitheatre Pkwy Mountain View",
    "state": "GA",
    "latitude": 37.4223895,
    "longitude": -122.0843123
  },
  "error": null
}