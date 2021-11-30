package com.olx.actuator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "advertisestats")
public class CustomAdvertiseStats {

	@ReadOperation
	public String getAdvertiseStats() {
		return mapOfAdvertises.toString();

	}



	private Map<String, String> mapOfAdvertises = new HashMap<String, String>();

	@PostConstruct
	public void initialize() {
		mapOfAdvertises.put("total_active_advertises", "10");
		mapOfAdvertises.put("total_closed_advertises", "19");
		mapOfAdvertises.put("total_open_advertises", "15");
	}

}
