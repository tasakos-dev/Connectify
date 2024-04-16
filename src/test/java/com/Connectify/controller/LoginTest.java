package com.Connectify.controller;



import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.Connectify.entity.User;
import com.Connectify.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testAuthenticationSuccess() throws Exception {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	String email = "user@example.com";
    	String password = "password";
    	User user = new User();
    	user.setEmail(email);
    	user.setPasswordHash(encoder.encode(password));
    	user.setPaidPlan(false);
    	
    	when(userService.findByEmail(email)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", email)
                .param("password", password))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed")); // Assuming successful authentication redirects to "/"
    }

    @Test
    void testAuthenticationFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", "invalidUser")
                .param("password", "invalidPassword"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error")); // Assuming your login failure URL redirects to /login?error
    }
}

