package com.olx.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.olx.entity.BlackListDocument;

public interface BlackListMongoRepo extends MongoRepository<BlackListDocument, String>{

}
