package com.olx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Blacklist;
import com.olx.entity.BlackListDocument;
import com.olx.repo.BlackListMongoRepo;

@Service
public class BlackListServiceImpl implements BlackListService{
	@Autowired
	BlackListMongoRepo blackListMongoRepo;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired 
	BlackListService blackListService;


	private BlackListDocument getBlackListDocumentFromDTO(Blacklist blacklist) {

		BlackListDocument blackListDocument = this.modelMapper.map(blacklist, BlackListDocument.class);

		return blackListDocument;
	}

	private Blacklist getBlackListDTOFromDocument(BlackListDocument blackListDocument) {

		Blacklist blacklist = this.modelMapper.map(blackListDocument, Blacklist.class);
		return blacklist;
	}

	private List<Blacklist> getDTOListFromBlackListDocumentList(List<BlackListDocument> blackListDocuments) {
		List<Blacklist> blackListDtoList = new ArrayList<Blacklist>();
		for (BlackListDocument blackListDocument : blackListDocuments) {
			blackListDtoList.add(getBlackListDTOFromDocument(blackListDocument));
		}
		return blackListDtoList;

	}

	@Override
	public boolean insertBlackListUserId(String authToken) {
		Blacklist blacklist = new Blacklist(authToken);
		blackListMongoRepo.save(getBlackListDocumentFromDTO(blacklist));
		return false;
	}

	@Override
	public boolean checkBlackListUser(String authToken) {
		Optional<BlackListDocument> blackListDocument=  blackListMongoRepo.findById(authToken);
		if (blackListDocument.isPresent()) {
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean logoutUser(String authToken) {
		//add the user to lack list
		blackListService.insertBlackListUserId(authToken);
		return true;
	}

}
