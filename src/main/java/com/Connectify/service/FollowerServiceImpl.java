package com.Connectify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Connectify.entity.User;
import com.Connectify.exception.UserNotExists;
import com.Connectify.entity.Follower;
import com.Connectify.repository.FollowerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowerServiceImpl extends FeedService implements FollowerService{
	private final FollowerRepository followerRepository;

    @Autowired
    public FollowerServiceImpl(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }
    
    @Override
    public void unfollowUser(String email, Long followerId) {
        // Retrieve the follower to unfollow
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));

        // Retrieve the user who wants to unfollow
        User user = userRepository.findByEmail(email);

        // Remove the follower from the user's followers list
        followerRepository.deleteByFollowerAndFollowing(follower, user);
        
    }
    
    @Override
    public void followUser(String email, String emailToFollow) throws UserNotExists {
        // Retrieve the user who wants to follow
        User user = userRepository.findByEmail(email);

        // Retrieve the user to follow by email
        User userToFollow = userRepository.findByEmail(emailToFollow);
        
        if (userToFollow == null) {
        	throw new UserNotExists();        
        }

        Follower newFollower = new Follower();
        newFollower.setFollowing(user);
        newFollower.setFollower(userToFollow);
        
        followerRepository.save(newFollower);
    }
    
    
    @Override
    public List<User> getUserFollowers(String email) {
    	List<User> followers = new ArrayList();
        User user = userRepository.findByEmail(email);
        for (Follower follower: followerRepository.findFollowersByFollowing(user)) {
        	followers.add(follower.getFollower());
        }
        	
        return followers;
    }
    
  



}
