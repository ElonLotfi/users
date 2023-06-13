package com.supra.usermanager.controller;

import com.supra.usermanager.entity.UserDto;
import com.supra.usermanager.repository.UserRepository;
import com.supra.usermanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
@RestController

public class UserController {


    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Adds a new user.
     *
     * @param user UserDto object containing user details.
     * @return ResponseEntity with a success message if the user is added successfully
     */
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto user) {
        long startTime = System.currentTimeMillis();
        // Validate input
        if (user.getName() == null || user.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Name is required");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        if (user.getCountry() == null || user.getCountry().isEmpty()) {
            return ResponseEntity.badRequest().body("Country is required");
        }

        if (user.getAge() == null || user.getAge().isEmpty()) {
            return ResponseEntity.badRequest().body("Age is required");
        }

        ResponseEntity<String> FORBIDDEN = verifyRegistration(user);
        if (FORBIDDEN != null) return FORBIDDEN;

        // Add user
        userService.addUser(user);

        long endTime = System.currentTimeMillis();
        logger.info("addUser - Input: " + user.toString() + ", Processing time: " + (endTime - startTime) + "ms");
        return ResponseEntity.ok("User added successfully");
    }

    /**
     * Verifies user registration eligibility based on age and country.
     *
     * @param user UserDto object containing user details.
     * @return ResponseEntity with a forbidden status and an error message if the user is not eligible to register,
     */
    private static ResponseEntity<String> verifyRegistration(UserDto user) {
        if (Integer.parseInt(user.getAge()) < 18 || !user.getCountry().equals("France")) {
            logger.error("User not allowed to register");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to register");
        }
        return null;
    }

    /**
     * Retrieves user details by ID.
     */

    @GetMapping("/userDetails/{id}")
    public ResponseEntity<?> getUserDetails(@PathVariable("id") String id) {
        long startTime = System.currentTimeMillis();
        // Validate input
        if (id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().body("User ID is required");
        }

        Optional<UserDto> user = userService.getUserById(id);
        if (user.isPresent()) {
            long endTime = System.currentTimeMillis();
            logger.info("Get User with Id :: " + id +" - user : " + user.toString() + ", Processing time: " + (endTime - startTime) + "ms");
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        long startTime = System.currentTimeMillis();
        List<UserDto> users = userService.getUsers();
        long endTime = System.currentTimeMillis();
        logger.info("Get All users " + ", Processing time: " + (endTime - startTime) + "ms");
        return ResponseEntity.ok(users);
    }
}
