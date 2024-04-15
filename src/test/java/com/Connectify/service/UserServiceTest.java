package com.Connectify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Connectify.entity.User;
import com.Connectify.entity.UserData;
import com.Connectify.repository.UserRepository;


@SpringBootTest
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	
	@Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testRegister() {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // Given
        UserData userData = new UserData();
        userData.setEmail("test@example.com");
        userData.setPassword("password");
        userData.setPaidPlan(true);

        User newUser = new User();
        newUser.setEmail(userData.getEmail());
        newUser.setPaidPlan(userData.isPaidPlan());
//        newUser.setPasswordHash("hashedPassword");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenReturn(newUser);
        

        // When
        userService.register(userData);

        // Then
        verify(userRepository).save(any(User.class));

        // Extract the captured argument
        User capturedUser = userCaptor.getValue();

        // Perform assertions on the captured user
        assertEquals(userData.getEmail(), capturedUser.getEmail());
        assertEquals(userData.isPaidPlan(), capturedUser.isPaidPlan());
        assertTrue(passwordEncoder.matches("password", capturedUser.getPasswordHash()));
        
    }

    @Test
    void testGetPlan() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPaidPlan(true);

        when(userRepository.findByEmail(email)).thenReturn(user);

        // When
        String plan = userService.getPlan(email);

        // Then
        assertEquals("Premium", plan);
    }

}
