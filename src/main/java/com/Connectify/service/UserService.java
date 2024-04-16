package com.Connectify.service;

import org.springframework.stereotype.Service;

import com.Connectify.entity.User;
import com.Connectify.entity.UserData;

/**
 * Service interface for managing users.
 * @author tasakos
 *
 */
@Service
public interface UserService {
	
	/**
     * Finds a user by email.
     *
     * @param email the email of the user to find
     * @return the user with the specified email, or null if not found
     */
	public User findByEmail(String email);
	
	  /**
     * Registers a new user.
     *
     * @param userData the data of the user to register
     */
	public void register(UserData userData);
	
	/**
     * Gets the plan of a user.
     *
     * @param username the username of the user
     * @return the plan of the user
     */
	public String getPlan(String username);
}
