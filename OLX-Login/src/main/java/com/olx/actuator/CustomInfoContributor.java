package com.olx.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.stereotype.Component;

@Component
public class CustomInfoContributor implements org.springframework.boot.actuate.info.InfoContributor{

	@Override
	public void contribute(Builder builder) {
		// TODO Auto-generated method stub
		//TODO userRepo.getUserInfoxxx();
		Map<String, Object> hm = new HashMap<>();
		hm.put("number of reg users", "total login count");
		builder.withDetails(hm);
		builder.toString();
		builder.build();
		
	}
	
	

}
