package com.Connectify.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.Connectify.entity.User;


@SpringBootTest
@TestPropertySource(
 locations = "classpath:applicationTest.properties")
class UserRepositoryTests {

	@Autowired
    private UserRepository userRepository;

	@BeforeEach
    void initEach() {
        // Reset database state before each test
        userRepository.deleteAll();
    }

    @Test
    public void testFindByEmail() {
    	User user = new User();

        user.setEmail("user@example.com");
        user.setPasswordHash("password");
        user.setPaidPlan(false);
        userRepository.save(user);
        
        User foundUser = userRepository.findByEmail("user@example.com");

        assertNotNull(foundUser);
        assertEquals("user@example.com", foundUser.getEmail());
    }
    
    @Test
    public void testNotFoundByEmail() {
        User foundUser = userRepository.findByEmail("notexist@example.com");

        assertNull(foundUser);
    }

}
