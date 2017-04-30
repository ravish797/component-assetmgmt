package com.assetmgmt.restresponse;

import org.springframework.stereotype.Service;

@Service
public class RestApiResponse 
{	
	private String message;
	private Object data;
	private String error;

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getData() 
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error) 
	{
		this.error = error;
	}
	
	public void clear()
	{
		this.setData(null);
		this.setError(null);
		this.setMessage(null);
	}
	
	
	
}
