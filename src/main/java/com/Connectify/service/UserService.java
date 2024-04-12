package com.Connectify.service;

import org.springframework.stereotype.Service;

import com.Connectify.entity.User;
import com.Connectify.entity.UserData;

@Service
public interface UserService {
	
	public User findByEmail(String email);
	public void register(UserData userData);
	public String getPlan(String username);
}
