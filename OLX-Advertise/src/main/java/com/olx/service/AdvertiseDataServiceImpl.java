package com.olx.service;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	@Autowired
	EntityManager entityManager;


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
				// advertiseEntity = getAdvertiseEntityFromDTO(newAdvertise);
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
			LocalDate dateCondition, LocalDate onDate) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<AdvertiseEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertiseEntity.class);
			Root<AdvertiseEntity> rootEntity = criteriaQuery.from(AdvertiseEntity.class);

			Predicate titlePredicate = criteriaBuilder.like(rootEntity.get("title"), "%" + searchText + "%");
			Predicate postedByPredicate = criteriaBuilder.like(rootEntity.get("userName"), "%" + postedBy + "%");
			Predicate categoryPredicate = criteriaBuilder.equal(rootEntity.get("categoryId"), categoryId);
			Predicate datePredicate = criteriaBuilder.equal(rootEntity.get("createdDate"), dateCondition);
			Predicate modifiedDatePredicate = criteriaBuilder.equal(rootEntity.get("modifiedDate"), onDate);
			Predicate finalPredicate = criteriaBuilder.or(titlePredicate, postedByPredicate, categoryPredicate,
					datePredicate, modifiedDatePredicate);

			CriteriaQuery<AdvertiseEntity> criteriaQuery2 = criteriaQuery.where(finalPredicate);
			TypedQuery<AdvertiseEntity> query = entityManager.createQuery(criteriaQuery2);
			List<AdvertiseEntity> advertiseEntityList = query.getResultList();
			List<Advertise> advertises = getDTOListFromEntityList(advertiseEntityList);
			return advertises;
		} catch (Exception e) {
			throw new ExceptionGeneric("exception occure while building the searchcriteria " + e);
		}
	}

	@Override
	public List<Advertise> getAdvertiseByFilter(String searchText) {
		try {

			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<AdvertiseEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertiseEntity.class);
			Root<AdvertiseEntity> rootEntity = criteriaQuery.from(AdvertiseEntity.class);

			Predicate titlePredicate = criteriaBuilder.like(rootEntity.get("title"), "%" + searchText + "%");
			Predicate postedByPredicate = criteriaBuilder.like(rootEntity.get("userName"), "%" + searchText + "%");
			Predicate describtionPredicate = criteriaBuilder.like(rootEntity.get("description"),
					"%" + searchText + "%");
			Predicate finalPredicate = criteriaBuilder.or(titlePredicate, postedByPredicate, describtionPredicate);

			CriteriaQuery<AdvertiseEntity> criteriaQuery2 = criteriaQuery.where(finalPredicate);
			TypedQuery<AdvertiseEntity> query = entityManager.createQuery(criteriaQuery2);
			List<AdvertiseEntity> advertiseEntityList = query.getResultList();
			List<Advertise> advertises = getDTOListFromEntityList(advertiseEntityList);
			return advertises;
		} catch (Exception e) {
			throw new ExceptionGeneric("exception occure while building the searchcriteria " + e);
		}

	}

}
