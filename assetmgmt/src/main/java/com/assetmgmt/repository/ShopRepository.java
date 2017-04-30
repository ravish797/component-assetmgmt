package com.assetmgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assetmgmt.bean.Shop;

public interface ShopRepository extends JpaRepository<Shop,String>
{

}
