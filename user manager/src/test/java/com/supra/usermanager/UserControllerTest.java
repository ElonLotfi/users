package com.supra.usermanager;

import com.supra.usermanager.controller.UserController;
import com.supra.usermanager.entity.UserDto;
import com.supra.usermanager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUserAndReturnsOkResponse() {
        // Mock user input
        UserDto user = new UserDto();
        user.setName("Lolo");
        user.setEmail("lolo@lolo.com");
        user.setPassword("password");
        user.setCountry("France");
        user.setAge("29");

        doNothing().when(userService).addUser(user);

        ResponseEntity<String> response = userController.addUser(user);

        verify(userService, times(1)).addUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User added successfully", response.getBody());
    }

    @Test
    public void testAddInvalidUserAndReturnsBadRequestResponse() {
        // Mock user input with missing required fields
        UserDto user = new UserDto();
        user.setName("Lolo");
        user.setEmail("");
        user.setPassword("password");
        user.setCountry("France");
        user.setAge("29");

        ResponseEntity<String> response = userController.addUser(user);

        verify(userService, never()).addUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email is required", response.getBody());
    }

    @Test
    public void testAddUser_NotAllowedToRegister_ReturnsForbiddenResponse() {
        // Mock user input with age < 18 and country other than France
        UserDto user = new UserDto();
        user.setName("Lolo");
        user.setEmail("lolo@lolo.com");
        user.setPassword("password");
        user.setCountry("Germany");
        user.setAge("16");

        ResponseEntity<String> response = userController.addUser(user);

        verify(userService, never()).addUser(user);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("You are not allowed to register", response.getBody());
    }

}
