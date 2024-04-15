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
	public UserServiceImpl(UserRepository userRepository) {
		super(userRepository);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	


	@Override
	public void register(UserData userData) {
		User newUser = new User();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(userData.getPassword());
	    
	    newUser.setEmail(userData.getEmail());
	    newUser.setPaidPlan(userData.isPaidPlan());
	    newUser.setPasswordHash(encodedPassword);
	    
	    userRepository.save(newUser);
	}
	
	  @Override
	    public String getPlan(String email) {
	    	return userRepository.findByEmail(email).isPaidPlan() ? "Premium" : "Free" ;
	    }
	    
	
	

}
