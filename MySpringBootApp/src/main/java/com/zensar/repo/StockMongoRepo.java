package com.zensar.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.zensar.entity.StockDocument;

public interface StockMongoRepo extends MongoRepository<StockDocument, Integer>{
	
	  List<StockDocument> findByName(String name);
	  
	  List<StockDocument> findByMarketName(String market);
	  
	  List<StockDocument> findByNameAndMarketName(String name, String market);
	  
		
		/*
		 * @Query(value = "SELECT * FROM Stock ORDER BY current_value", nativeQuery =
		 * true) List<StockDocument> sortStocksByPriceDesc();
		 */
		  
		/*
		 * @Query(value = "SELECT se from StockEntity se WHERE se.name LIKE %:name%")
		 * List<StockDocument> findByNameLike(@Param("name") String stockName);
		 */
		 
	  
	  List<StockDocument> findByNameContaining(String name);
	  
	  List<StockDocument> findByNameIsContaining(String name);
	  
	  List<StockDocument> findByNameContains(String name);
	  
	  List<StockDocument> findByOrderByNameAsc();
	  
	  List<StockDocument> findByOrderByNameDesc();
	 

}
