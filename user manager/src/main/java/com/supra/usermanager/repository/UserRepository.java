package com.supra.usermanager.repository;

import com.supra.usermanager.entity.UserDto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDto, String> {
}


