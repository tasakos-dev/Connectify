package com.Connectify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.Connectify.entity.Follower;
import com.Connectify.entity.User;
import com.Connectify.exception.UserNotExistsException;
import com.Connectify.repository.FollowerRepository;
import com.Connectify.repository.UserRepository;

@SpringBootTest
class FollowerServiceTest {
	
	@Mock
	private FollowerRepository followerRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Autowired
	private FollowerService followerService;
	
	@BeforeEach
	void setUp() {
		followerService = new FollowerServiceImpl(followerRepository, userRepository);
	}
	
	@Test
    void testFollowUser_Success() throws UserNotExistsException {
        // Arrange
        String userEmail = "user@example.com";
        String userToFollowEmail = "followed@example.com";
        User user = new User();
        user.setEmail(userEmail);
        User userToFollow = new User();
        userToFollow.setEmail(userToFollowEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(user);
        when(userRepository.findByEmail(userToFollowEmail)).thenReturn(userToFollow);

        // Act
        followerService.followUser(userEmail, userToFollowEmail);

        // Assert
        verify(userRepository).findByEmail(userEmail);
        verify(userRepository).findByEmail(userToFollowEmail);
        verify(followerRepository).save(any(Follower.class));
    }
	
	@Test
    void testFollowUser_UserNotExists() {
        // Arrange
        String email = "user@example.com";
        String emailToFollow = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(new User());
        when(userRepository.findByEmail(emailToFollow)).thenReturn(null);

        // Act and Assert
        assertThrows(UserNotExistsException.class, () -> followerService.followUser(email, emailToFollow));
    }
	
	@Test
	void testUnfollowUser_Success() {
	    // Arrange
	    String email = "user@example.com";
	    Long followerId = 1L;
	    User follower = new User();
	    User user = new User();
	    when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
	    when(userRepository.findByEmail(email)).thenReturn(user);

	    // Act
	    followerService.unfollowUser(email, followerId);

	    // Assert
	    verify(userRepository).findById(followerId);
	    verify(userRepository).findByEmail(email);
	    verify(followerRepository).deleteByFollowerAndFollowing(follower, user);
	}
	

	@Test
	void testGetUserFollowers() {
	    // Arrange
	    String followerEmail = "follower@example.com";
	    String followingEmail = "following@example.com";
	    User following = new User();
	    following.setEmail(followingEmail);
	    
	    User follower = new User();
	    follower.setEmail(followerEmail);
	
	    Follower followerRelationship = new Follower();
	    followerRelationship.setFollowing(following);
	    followerRelationship.setFollower(follower);
	    
	    List<Follower> followerList = new ArrayList<>();
	    followerList.add(followerRelationship);
	    when(userRepository.findByEmail(followerEmail)).thenReturn(following);
	    when(followerRepository.findFollowersByFollowing(any())).thenReturn(followerList);
	    
//	    System.err.println(followerRepository.findFollowersByFollowing(following).get(0));/
	    // Act
	    List<User> followers = followerService.getUserFollowers(followerEmail);

	    // Assert
	    assertNotNull(followers);
	    assertEquals(1, followers.size());
	    assertEquals(followerEmail, followers.get(0).getEmail());
	}
	
	

}
