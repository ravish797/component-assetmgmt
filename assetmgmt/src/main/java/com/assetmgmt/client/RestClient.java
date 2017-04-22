package com.assetmgmt.client;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

/**
 * @author rchugh
 */
@Service
public class RestClient 
{
	 /**
     * Gets the rest connection for calling google api to fetch longitude and latitude.
     *
     * @param completeUrl
     * @return HttpURLConnection
     */
	public HttpURLConnection getRestConnection(String completeUrl) throws IOException
	{
		URL url = new URL(completeUrl);
		HttpURLConnection conn = getConnection(url);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");	
		
		return conn;
	}
	
	/**
     * gets the HttpURLConnection to make third party rest call
     *
     * @param url
     * @return HttpURLConnection
     */
	private HttpURLConnection getConnection(URL url) throws IOException
	{		
		return (HttpURLConnection) url.openConnection(); 
	}
}
