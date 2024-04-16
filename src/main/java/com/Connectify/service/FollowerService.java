package com.Connectify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Connectify.entity.User;
import com.Connectify.exception.UserNotExistsException;

/**
 * Service interface for managing followers.
 * @author tasakos
 *
 */
@Service
public interface FollowerService {
	
	/**
     * Unfollows a user.
     *
     * @param username   the username of the user performing the action
     * @param followerId the ID of the user to unfollow
     */
	public void unfollowUser(String username, Long followerId);
	
	/**
     * Follows a user.
     *
     * @param username the username of the user performing the action
     * @param email    the email of the user to follow
     * @throws UserNotExistsException if the user to be followed does not exist
     */
	public void followUser(String username, String email) throws UserNotExistsException;
	
	/**
     * Retrieves the followers of a user.
     *
     * @param username the username of the user
     * @return a list of users who follow the specified user
     */
	public List<User> getUserFollowers(String username);
}
