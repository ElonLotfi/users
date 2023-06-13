package com.supra.usermanager.service;

import com.supra.usermanager.entity.UserDto;
import com.supra.usermanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Add user
     */
    public void addUser(UserDto user) {
        userRepository.save(user);
    }
    /**
     * Get user by id
     */
    public Optional<UserDto> getUserById(String id) {
        return userRepository.findById(id);
    }
    /**
     * Add All users
     */
    public List<UserDto> getUsers() {
        return userRepository.findAll();
    }
}
