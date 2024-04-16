package com.Connectify.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@SpringBootTest
@AutoConfigureMockMvc
class ConnectifyControllerTest {

	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
		//System.exit(0);
    }
	    @Test
	    void testWelcome() throws Exception {
	        mockMvc.perform(MockMvcRequestBuilders.get("/"))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/index.html"));
	    }
}
