package com.zensar.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zensar.entity.StockEntity;

public interface StockRepo extends JpaRepository<StockEntity, Integer> {

	List<StockEntity> findByName(String name);

	List<StockEntity> findByMarketName(String market);

	List<StockEntity> findByNameAndMarketName(String name, String market);

	@Query(value = "SELECT * FROM Stock ORDER BY current_value", nativeQuery = true)
	List<StockEntity> sortStocksByPriceDesc();

	@Query(value = "SELECT se from StockEntity se WHERE se.name LIKE %:name%")
	List<StockEntity> findByNameLike(@Param("name") String stockName);

	List<StockEntity> findByNameContaining(String name);

	List<StockEntity> findByNameIsContaining(String name);

	List<StockEntity> findByNameContains(String name);

	List<StockEntity> findByOrderByNameAsc();

	List<StockEntity> findByOrderByNameDesc();

}
