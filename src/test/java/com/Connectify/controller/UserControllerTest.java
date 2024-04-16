package com.Connectify.controller;


import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Connectify.entity.User;
import com.Connectify.entity.UserData;
import com.Connectify.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    

    @Test
    void testShowForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/show_form"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/register_form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user_data"));
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        UserData userData = new UserData();
        userData.setPassword("password");
        userData.setEmail("test@example.com");
        userData.setPaidPlan(false);
        when(userService.findByEmail("test@example.com")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .param("email", "test@example.com")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/index.html"));
    }

    @Test
    void testRegisterUser_BlankEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .param("email", "")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/register_form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));
    }
    
    @Test
    void testRegisterUser_BlankPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .param("email", "test@example.com")
                .param("password", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/register_form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));
    }

    @Test
    void testRegisterUser_InvalidEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .param("email", "invalid_email")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/register_form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));
    }

    @Test
    void testRegisterUser_UserExists() throws Exception {
    	UserData userData = new UserData();
        userData.setPassword("password");
        userData.setEmail("test@example.com");
        userData.setPaidPlan(false);
        when(userService.findByEmail("existing_user@example.com")).thenReturn(new User());
        mockMvc.perform(MockMvcRequestBuilders.post("/users/register")
                .param("email", "existing_user@example.com")
                .param("password", "password"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/register_form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));
    }


}
