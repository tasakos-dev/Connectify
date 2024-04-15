package com.Connectify.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import com.Connectify.entity.Follower;
import com.Connectify.entity.User;

@SpringBootTest
@TestPropertySource(
		 locations = "classpath:applicationTest.properties")
class FollowerRepositoryTests {

	 @Autowired
	 private FollowerRepository followerRepository;
	 @Autowired
	 private UserRepository userRepository;
	 
	 @BeforeEach
	    void initEach() {
	        // Reset database state before each test
	        userRepository.deleteAll();
	        followerRepository.deleteAll();
	    }

	    @Test
	    @DirtiesContext
	    public void testFindByFollower() {
	    	User follower = new User();
	        follower.setEmail("follower1@example.com");
	        follower.setPasswordHash("password1");
	        follower.setPaidPlan(false);

	        userRepository.save(follower);
	

	        User following = new User();
	
	        following.setEmail("following1@example.com");
	        following.setPasswordHash("password2");
	        following.setPaidPlan(false);

	        userRepository.save(following);
			
	        

	        // Save a follower relationship
	        Follower newFollower = new Follower();
	        newFollower.setFollowing(following);
	        newFollower.setFollower(follower);
	        followerRepository.save(newFollower);
	        List<Follower> followers = followerRepository.findByFollower(follower);
	        assertEquals(1, followers.size()); // Assuming each follower follows 5 users
	    }

	    @Test
	    @DirtiesContext
	    public void testFindByFollowing() {
	    	User follower = new User();
	        follower.setEmail("follower1@example.com");
	        follower.setPasswordHash("password1");
	        follower.setPaidPlan(false);

	        userRepository.save(follower);
	

	        User following = new User();
	
	        following.setEmail("following1@example.com");
	        following.setPasswordHash("password2");
	        following.setPaidPlan(false);

	        userRepository.save(following);
			
	        

	        // Save a follower relationship
	        Follower newFollower = new Follower();
	        newFollower.setFollowing(following);
	        newFollower.setFollower(follower);
	        followerRepository.save(newFollower);

	        List<Follower> followers = followerRepository.findByFollowing(following);
	        assertEquals(1, followers.size()); // Assuming 5 followers for each user
	    }

	    @Test
	    @DirtiesContext
	    public void testFindFollowingByFollower() {
	    	User follower = new User();
	        follower.setEmail("follower1@example.com");
	        follower.setPasswordHash("password1");
	        follower.setPaidPlan(false);

	        userRepository.save(follower);
	

	        User following = new User();
	
	        following.setEmail("following1@example.com");
	        following.setPasswordHash("password2");
	        following.setPaidPlan(false);

	        userRepository.save(following);
			
	        

	        // Save a follower relationship
	        Follower newFollower = new Follower();
	        newFollower.setFollowing(following);
	        newFollower.setFollower(follower);
	        followerRepository.save(newFollower);
	        List<Follower> followingList = followerRepository.findFollowingByFollower(follower);
	        assertEquals(1, followingList.size()); // Assuming each follower follows 5 users
	    }

	    @Test
	    @DirtiesContext
	    public void testFindFollowersByFollowing() {
	    	User follower = new User();
	        follower.setEmail("follower1@example.com");
	        follower.setPasswordHash("password1");
	        follower.setPaidPlan(false);

	        userRepository.save(follower);
	

	        User following = new User();
	
	        following.setEmail("following1@example.com");
	        following.setPasswordHash("password2");
	        following.setPaidPlan(false);

	        userRepository.save(following);
			
	        

	        // Save a follower relationship
	        Follower newFollower = new Follower();
	        newFollower.setFollowing(following);
	        newFollower.setFollower(follower);
	        followerRepository.save(newFollower);


	        List<Follower> followersList = followerRepository.findFollowersByFollowing(following);
	        assertEquals(1, followersList.size()); // Assuming 5 followers for each user
	    }
	    
	    @Test
	    @DirtiesContext
	    public void testDeleteByFollowerAndFollowing() {
	        // Create two users
	        User follower = new User();
	        follower.setEmail("follower1@example.com");
	        follower.setPasswordHash("password1");
	        follower.setPaidPlan(false);

	        userRepository.save(follower);
	

	        User following = new User();
	
	        following.setEmail("following1@example.com");
	        following.setPasswordHash("password2");
	        following.setPaidPlan(false);

	        userRepository.save(following);
			
	        

	        // Save a follower relationship
	        Follower newFollower = new Follower();
	        newFollower.setFollowing(following);
	        newFollower.setFollower(follower);
	        followerRepository.save(newFollower);

	        // Delete the follower relationship
	        followerRepository.deleteByFollowerAndFollowing(follower, following);

	        // Verify that the relationship is deleted
	        List<Follower> followingList = followerRepository.findFollowingByFollower(follower);
	        assertEquals(0, followingList.size());
	    }
	    
}
