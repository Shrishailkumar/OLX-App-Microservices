package com.zensar.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.zensar.dto.Stock;
import com.zensar.exception.InvalidStockIdException;
import com.zensar.service.StockMongoService;
import com.zensar.service.StockService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/zenmarketapp")
public class StockController {
	@Autowired
	@Qualifier("MONGO_SERVICE")
	StockMongoService stockService;

	@ExceptionHandler(value = { InvalidStockIdException.class })
	public ResponseEntity<String> handleInvalidStockIdError(RuntimeException exception, WebRequest request) {
		return new ResponseEntity<String>("local invalid stock id excption", HttpStatus.BAD_REQUEST);

	}

	@GetMapping(value = "/stock", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "get all stock")
	public Collection<Stock> getAllStocks() {
		return stockService.getAllStock();
	}

	@GetMapping(value = "/stock/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "get a stock by id stock")
	public Stock getStockByID(@PathVariable("id") int stockID) {
		return stockService.getStockById(stockID);
	}

	@PostMapping(value = "/stock", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "create a new stock")
	public @ResponseBody ResponseEntity<Stock> createNewStock(@RequestBody Stock stock) {
		stockService.createNewStock(stock);
		return new ResponseEntity(stock, HttpStatus.CREATED);
	}

	@PutMapping(value = "/stock/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "update a stock by id stock")
	public Stock updateStock(@PathVariable("id") int stockID, @RequestBody Stock newStock) {
		return stockService.updateStock(stockID, newStock);
	}

	@DeleteMapping(value = "/stock/{id}")
	@ApiOperation(value = "delete a stock by id stock")
	public boolean deleteStockById(@PathVariable("id") int stockID) {
		return stockService.deleteStockById(stockID);
	}

	@DeleteMapping(value = "/stock")
	@ApiOperation(value = "delete all stock")
	public boolean deleteStockById() {
		return stockService.deleteAllStocks();
	}


	@GetMapping(value = "/stock/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Stock> getStocksByName(@PathVariable("name") String name) {
		return stockService.findByName(name);
	}

	@GetMapping(value = "/stock/marketname/{marketname}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Stock> getStocksByMarketName(@PathVariable("marketname") String marketname) {
		return stockService.findByMarketName(marketname);
	}

	@GetMapping(value = "/stock/marketname/{marketname}/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Stock> getStocksByNameAndMarketName(@PathVariable("marketname") String marketname,
			@PathVariable("name") String name) {
		return stockService.findByNameAndMarketName(name, marketname);
	}

	/*
	 * @GetMapping(value = "/stock/name/like/{name}", produces =
	 * MediaType.APPLICATION_JSON_VALUE) public List<Stock>
	 * getStocksByNameLike(@PathVariable("name") String name) { return
	 * stockService.findByNameLike(name); }
	 */

	@GetMapping(value = "/stock/name/sort/{sortType}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Stock> getStocksOrderByName(@PathVariable("sortType") String sortType) {
		return stockService.findByOrderByName(sortType);
	}

	@GetMapping(value = "/stock/page/{startIndex}/{records}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Stock> getStocksByPage(@PathVariable("startIndex") int startIndex,
			@PathVariable("records") int records) {
		return stockService.findByPage(startIndex, records);
	}

}
