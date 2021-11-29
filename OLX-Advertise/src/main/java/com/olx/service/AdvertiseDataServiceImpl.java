package com.olx.service;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.Utility;
import com.olx.dto.Advertise;
import com.olx.entity.AdvertiseEntity;
import com.olx.exception.ExceptionGeneric;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.repo.AdvertiseRepo;

import springfox.documentation.spring.web.WebMvcNameValueExpressionWrapper;

@Service
public class AdvertiseDataServiceImpl implements AdvertiseDataService {

	@Autowired
	MasterDataDeligate masterDataDeligate;
	@Autowired
	LoginDeligate loginDeligate;

	@Autowired
	AdvertiseRepo advertiseRepo;

	/*
	 * public static int lastAdvertisement; private boolean isUserLoggeedIn = true;
	 * public static Map<Integer, Advertise> advertiseMap = new HashMap<>();
	 * 
	 * static { advertiseMap.put(1, new Advertise(1, "Umbrella", 20, 1,
	 * "Umbrella for sale", "Shri", Utility.getLocaleDate(),
	 * Utility.getLocaleDate(), "open")); advertiseMap.put(2, new Advertise(2,
	 * "Pen", 10, 2, "pen for sale", "neha", Utility.getLocaleDate(),
	 * Utility.getLocaleDate(), "open")); advertiseMap.put(3, new Advertise(3,
	 * "Books", 20, 1, "Books for sale", "navisha", Utility.getLocaleDate(),
	 * Utility.getLocaleDate(), "open")); advertiseMap.put(4, new Advertise(4, "TV",
	 * 20000, 2, "Infotainment for sale", "raghu", Utility.getLocaleDate(),
	 * Utility.getLocaleDate(), "open")); lastAdvertisement = advertiseMap.size();
	 * 
	 * }
	 */

	// ----------- helper methods of modal.mapper
	@Autowired
	ModelMapper modelMapper;

	private AdvertiseEntity getAdvertiseEntityFromDTO(Advertise advertise) {

		AdvertiseEntity advertiseEntity = this.modelMapper.map(advertise, AdvertiseEntity.class);

		return advertiseEntity;
	}

	private Advertise getAdvertiseDTOFromEntity(AdvertiseEntity adevertiseEntity) {

		Advertise advertise = this.modelMapper.map(adevertiseEntity, Advertise.class);
		return advertise;
	}

	private List<Advertise> getDTOListFromEntityList(List<AdvertiseEntity> advertiseEntityList) {
		List<Advertise> advertiseDtoList = new ArrayList<Advertise>();
		for (AdvertiseEntity advertiseEntity : advertiseEntityList) {
			advertiseDtoList.add(getAdvertiseDTOFromEntity(advertiseEntity));
		}
		return advertiseDtoList;

	}

	// helper methds end of for model mapper

	@Override
	public Advertise createAdvertiseItem(Advertise advertise, String AuthToken) {

		if (loginDeligate.validateToken(AuthToken)) {
			advertise.setUserName(loginDeligate.getUserName(AuthToken));
			advertise.setModifiedDate(Utility.getLocaleDate());
			advertise.setCreatedDate(Utility.getLocaleDate());
			AdvertiseEntity advertiseEntity = getAdvertiseEntityFromDTO(advertise);
			advertiseRepo.save(advertiseEntity);
			return getAdvertiseDTOFromEntity(advertiseEntity);
		} else {
			throw new InvalidAuthTokenException();
		}

	}

	@Override
	public Advertise updateAdvertiseItem(int id, String AuthToken, Advertise newAdvertise) {
		
		if (loginDeligate.isUserLoggedIn(AuthToken)) {
			Optional<AdvertiseEntity> optionalEntity = advertiseRepo.findById(id);
			if (optionalEntity.isPresent()) {
				AdvertiseEntity advertiseEntity = optionalEntity.get();
				advertiseEntity.setId(id);
				advertiseEntity.setDescription(newAdvertise.getDescription());
				advertiseEntity.setPrice(newAdvertise.getPrice());
				advertiseEntity.setTitle(newAdvertise.getTitle());
				advertiseEntity.setModifiedDate(newAdvertise.getModifiedDate());
				//advertiseEntity = getAdvertiseEntityFromDTO(newAdvertise);
				advertiseEntity = advertiseRepo.save(advertiseEntity);
				Advertise advertise = getAdvertiseDTOFromEntity(advertiseEntity);
				return advertise;
			} else {
				throw new ExceptionGeneric("Stock is not present");
			}
		} else {
			throw new InvalidAuthTokenException("invalid auth token");
		}
	}

	@Override
	public Collection<Advertise> getAllAdvertise(String AuthToken) {
		if (loginDeligate.isUserLoggedIn(AuthToken)) {
			List<AdvertiseEntity> advertiseEntityList = this.advertiseRepo.findAll();
			return getDTOListFromEntityList(advertiseEntityList);
		} else {
			throw new InvalidAuthTokenException("invalid auth token");
		}

	}

	@Override
	public Advertise getAdvertiseByID(int id, String AuthToken) {
		Optional<AdvertiseEntity> entity = advertiseRepo.findById(id);
		if (entity.isPresent()) {
			AdvertiseEntity advertiseEntity = entity.get();
			Advertise advertiseDto = this.modelMapper.map(advertiseEntity, Advertise.class);
			return advertiseDto;
		} else {
			throw new InvalidAuthTokenException("invalid auth token");
		}
	}

	@Override
	public boolean deleteAdvertise(int id, String AuthToken) {
		if (loginDeligate.isUserLoggedIn(AuthToken)) {
			advertiseRepo.deleteById(id);
			return true;
		} else
			return false;

	}

	@Override
	public boolean deleteAdvertiseAnyUser(int id, String AuthToken) {
		if (loginDeligate.validateToken(AuthToken)) {
			advertiseRepo.deleteById(id);
			return true;
		} else
			return false;
	}

	@Override
	public List<Advertise> getAdvertiseByFilter(String searchText, int categoryId, String postedBy,
			String dateCondition, LocalDate onDate) {
		List<Advertise> filteredList;
		List<Advertise> advertisesList = new ArrayList<Advertise>(getDTOListFromEntityList(advertiseRepo.findAll()));
		List<String> searchStringList = new ArrayList<>();
		searchStringList.add(searchText);
		searchStringList.add(postedBy);
		searchStringList.add(dateCondition);

		filteredList = advertisesList.stream()
				.filter(advertise -> searchStringList.contains(advertise.getTitle())
						|| searchText.contains(advertise.getCreatedDate().toString())
						|| searchText.contains(advertise.getDescription())
						|| searchStringList.contains(advertise.getUserName()))
				.collect(Collectors.toList());

		return filteredList;
	}

	@Override
	public List<Advertise> getAdvertiseByFilter(String searchText) {
		List<Advertise> filteredList;
		List<Advertise> advertisesList = new ArrayList<Advertise>(getDTOListFromEntityList(advertiseRepo.findAll()));

		filteredList = advertisesList.stream()
				.filter(advertise -> searchText.contains(advertise.getTitle())
						|| searchText.contains(advertise.getCreatedDate().toString())
						|| searchText.contains(advertise.getDescription())
						|| searchText.contains(advertise.getUserName()))
				.collect(Collectors.toList());

		return filteredList;
	}

}
