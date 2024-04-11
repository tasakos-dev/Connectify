package com.Connectify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Connectify.entity.User;
import com.Connectify.exception.UserNotExists;

@Service
public interface FollowerService {
	public void unfollowUser(String username, Long followerId);
	public void followUser(String username, String email) throws UserNotExists;
	public List<User> getUserFollowers(String username);

}
