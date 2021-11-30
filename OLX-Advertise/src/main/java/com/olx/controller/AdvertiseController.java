package com.olx.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.naming.directory.InvalidSearchFilterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Advertise;
import com.olx.service.AdvertiseDataService;
import com.olx.service.LoginDeligateImpl;
import com.olx.service.MasterDataDeligateImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("olx/advertises")
public class AdvertiseController {

	@Autowired
	AdvertiseDataService advertiseDataService;
	@Autowired
	MasterDataDeligateImpl masterDataDeligateImpl;

	@PostMapping(value = "/advertise", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces ={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value="Create an Advertise")
	public Advertise createAdvertiseItem(@RequestBody Advertise advertise, @RequestHeader("Authorization") String AuthToken) {
		return advertiseDataService.createAdvertiseItem(advertise, AuthToken);
	}

	// update existing
	@PutMapping(value = "/advertise/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}, produces ={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value="Update an Advertise")
	public Advertise updateAdvertiseItem(@PathVariable("id") int id, @RequestHeader("Authorization") String AuthToken,
			@RequestBody Advertise newAdvertise) {
		return advertiseDataService.updateAdvertiseItem(id, AuthToken, newAdvertise);
	}

	// getAllAdvertisements()
	@GetMapping(value = "/user/advertise", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value="get all Advertises")
	public Collection<Advertise> getAllAdvertise(@RequestHeader("Authorization") String AuthToken) {
		return advertiseDataService.getAllAdvertise(AuthToken);
	}

	// get specific advertisement /user/advertise/{advertiseId}
	@GetMapping(value = "/user/advertise/{advertiseId}", produces ={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value="Update an Advertise by id")
	public Advertise getAdvertiseByID(@PathVariable("advertiseId") int id, @RequestHeader("Authorization") String AuthToken) {
		return advertiseDataService.getAdvertiseByID(id, AuthToken);
	}

	// delete a specific advertisement, /user/advertise/{advertiseId}
	@DeleteMapping(value = "/user/advertise/{advertiseId}")
	@ApiOperation(value="delete an Advertise for logged in user")
	public boolean deleteAdvertise(@PathVariable("advertiseId") int id, @RequestHeader("Authorization") String AuthToken) {
		return advertiseDataService.deleteAdvertise(id, AuthToken);
	}

	// delete a specific add for any user /advertise/{advertiseId}
	@DeleteMapping(value = "/advertise/{advertiseId}")
	@ApiOperation(value="delete an Advertise")
	public boolean deleteAdvertiseAnyUser(@PathVariable("advertiseId") int id,
			@RequestHeader("Authorization") String AuthToken) {
		return advertiseDataService.deleteAdvertiseAnyUser(id, AuthToken);
	}

	// filtr the item on criteria , //advertise/search/filtercriteria
	@GetMapping(value = "/advertise/search/filtercriteria", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value="get Advertise by any filter criterias")
	public List<Advertise> getAdvertiseByFilter(@RequestParam("searchText") String searchText,
			@RequestParam(name = "categoryId", required = false) int categoryId,
			@RequestParam("postedBy") String postedBy,@RequestParam("dateCondition") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCondition,
			@RequestParam("onDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate onDate) {

		return advertiseDataService.getAdvertiseByFilter(searchText, categoryId, postedBy, dateCondition, onDate);
	}

	// filter the ads on searchtxt criteria , //advertise/search/filtercriteria
	@GetMapping(value = "/advertise/search", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value="get Advertise by a search text filter criteria")
	public List<Advertise> getAdvertiseByFilter(@RequestParam("searchText") String searchText) throws InvalidSearchFilterException {
		if(searchText!=null)
		return advertiseDataService.getAdvertiseByFilter(searchText);
	
	  else { 
		  throw new InvalidSearchFilterException("the text is not in specific format"); }
	 
	}
	
	@GetMapping(value = "/advertise/category",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value="Intermicro service call to masterdata getAllCategory")
	public List<Map> getAllCategory(){	
		return masterDataDeligateImpl.getAllCategory();
	}

	
	
}
