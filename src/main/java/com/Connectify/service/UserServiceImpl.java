package com.Connectify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Connectify.entity.User;
import com.Connectify.entity.UserData;
import com.Connectify.repository.UserRepository;

@Service
public class UserServiceImpl extends FeedService implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	public UserServiceImpl() {
		super();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User findByName(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public void register(UserData userData) {
		User newUser = new User();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(userData.getPassword());
	    
	    newUser.setUsername(userData.getName());
	    newUser.setEmail(userData.getEmail());
	    newUser.setPaidPlan(userData.isPaidPlan());
	    newUser.setPasswordHash(encodedPassword);
	    
	    userRepository.save(newUser);
	}
	
	  @Override
	    public String getPlan(String username) {
	    	return userRepository.findByUsername(username).isPaidPlan() ? "Premium" : "Free" ;
	    }
	    
	
	

}
