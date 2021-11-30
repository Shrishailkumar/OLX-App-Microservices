package com.olx.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.olx.dto.Advertise;

public interface AdvertiseDataService {

	public Advertise createAdvertiseItem(Advertise advertise, String AuthToken);

	public Advertise updateAdvertiseItem(int id, String AuthToken, @RequestBody Advertise newAdvertise);

	public Collection<Advertise> getAllAdvertise(String AuthToken);

	public Advertise getAdvertiseByID(int id, String AuthToken);

	public boolean deleteAdvertise(int id, String AuthToken);

	public boolean deleteAdvertiseAnyUser(int id, String AuthToken);

	public List<Advertise> getAdvertiseByFilter(String searchText, int categoryId, String postedBy,
			LocalDate dateCondition, LocalDate onDate);

	public List<Advertise> getAdvertiseByFilter(String searchText);

}
