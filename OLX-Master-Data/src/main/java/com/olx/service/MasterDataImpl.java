package com.olx.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.entity.CategoryEntity;
import com.olx.entity.StatusEntity;
import com.olx.repo.MasterDataRepo;
import com.olx.repo.MasterDataRepoStatus;

@Service
public class MasterDataImpl implements MasterDataService {

	@Autowired
	MasterDataRepo masterDataRepo;
	
	@Autowired
	MasterDataRepoStatus masterDataRepoStatus;
	
	@Autowired
	ModelMapper modelMapper;

	private CategoryEntity getCategoryEntityFromDTO(Category category) {

		CategoryEntity categoryEntity = this.modelMapper.map(category, CategoryEntity.class);

		return categoryEntity;
	}

	private Category getCategoryDTOFromEntity(CategoryEntity categoryEntity) {

		Category category = this.modelMapper.map(categoryEntity, Category.class);
		return category;
	}

	private List<Category> getDTOListFromEntityList(List<CategoryEntity> stockentityList) {
		List<Category> categoryDtoList = new ArrayList<Category>();
		for (CategoryEntity categoryEntity : stockentityList) {
			categoryDtoList.add(getCategoryDTOFromEntity(categoryEntity));
		}
		return categoryDtoList;

	}

	private StatusEntity getStatusEntityFromDTO(Status category) {

		StatusEntity entity = this.modelMapper.map(category, StatusEntity.class);

		return entity;
	}

	private Status getStatusDTOFromEntity(StatusEntity entity) {

		Status status = this.modelMapper.map(entity, Status.class);
		return status;
	}

	private List<Status> getDTOListFromStatusEntityList(List<StatusEntity> entityList) {
		List<Status> dtoList = new ArrayList<Status>();
		for (StatusEntity categoryEntity : entityList) {
			dtoList.add(getStatusDTOFromEntity(categoryEntity));
		}
		return dtoList;

	}

	@Override
	public List<Category> getAllCategory() {
		List<CategoryEntity> categoryEntityList = this.masterDataRepo.findAll();
		return getDTOListFromEntityList(categoryEntityList);
	}

	@Override
	public List<Status> getStatus() {
		List<Status> status = new ArrayList<Status>();
		List<StatusEntity> statusEntityList = this.masterDataRepoStatus.findAll();
		return getDTOListFromStatusEntityList(statusEntityList);
	}

}
