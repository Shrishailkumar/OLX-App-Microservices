package com.olx.service;

import java.util.List;

import com.olx.dto.Category;
import com.olx.dto.Status;

public interface MasterDataService {
	public List<Category> getAllCategory();
	public List<Status> getStatus();
	

}
