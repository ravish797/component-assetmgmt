package com.assetmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author rchugh
 */
@SpringBootApplication
@PropertySource("classpath:component.properties")
public class AssetManagementServiceApplication 
{
	public static void main(String args[])
	{
		SpringApplication.run(AssetManagementServiceApplication.class, args);
	}
}
