package com.Connectify.service;

import com.Connectify.entity.User;
import com.Connectify.entity.UserData;

public interface UserService {
	
	public User findByEmail(String email);
	
	public User findByName(String name);
	
	public void register(UserData userData);

}
