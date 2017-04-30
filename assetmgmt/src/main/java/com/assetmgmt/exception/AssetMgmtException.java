package com.assetmgmt.exception;

public class AssetMgmtException extends RuntimeException 
{
	
	private String messageKey;	   

	public AssetMgmtException(String messageKey, Throwable cause) 
	{
        super(messageKey, cause);
        this.messageKey = messageKey;
    }

    public AssetMgmtException(String messageKey) 
    {
        super(messageKey);
        this.messageKey = messageKey;
    }
}
