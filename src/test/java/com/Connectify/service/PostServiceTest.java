package com.Connectify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import com.Connectify.entity.Post;
import com.Connectify.entity.User;
import com.Connectify.repository.FollowerRepository;
import com.Connectify.repository.PostRepository;
import com.Connectify.repository.UserRepository;

@SpringBootTest
class PostServiceTest {
	  	@Mock
	    private PostRepository postRepository;

	    @Mock
	    private FollowerRepository followerRepository;

	    @Mock
	    private UserRepository userRepository;

	    @Autowired
	    private PostService postService;

	    @BeforeEach
	    void setUp() {
	        postService = new PostServiceImpl(postRepository, followerRepository, userRepository);
	    }

	    @Test
	    void testGetFollowedPosts() {
	        // Arrange
	        String email = "user@example.com";
	        User user = new User();
	        List<User> followingUsers = new ArrayList();
	        List<Post> expectedPosts = new ArrayList<>();
	        when(userRepository.findByEmail(email)).thenReturn(user);
	        when(followerRepository.findFollowingByFollower(user)).thenReturn(new ArrayList<>());
	        when(postRepository.findByUserInOrderByCreatedAtDesc(followingUsers)).thenReturn(expectedPosts);

	        // Act
	        List<Post> actualPosts = postService.getFollowedPosts(email);

	        // Assert
	        assertEquals(expectedPosts, actualPosts);
	        verify(userRepository).findByEmail(email);
	        verify(followerRepository).findFollowingByFollower(user);
	        verify(postRepository).findByUserInOrderByCreatedAtDesc(followingUsers);
	    }

	    @Test
	    void testGetUserPosts() {
	        // Arrange
	        String email = "user@example.com";
	        User user = new User();
	        List<Post> expectedPosts = new ArrayList<>();
	        when(userRepository.findByEmail(email)).thenReturn(user);
	        when(postRepository.findByUserOrderByCreatedAtDesc(user)).thenReturn(expectedPosts);

	        // Act
	        List<Post> actualPosts = postService.getUserPosts(email);

	        // Assert
	        assertEquals(expectedPosts, actualPosts);
	        verify(userRepository).findByEmail(email);
	        verify(postRepository).findByUserOrderByCreatedAtDesc(user);
	    }

	    @Test
	    void testCreatePost() {
	        // Arrange
	        String content = "Test post";
	        String email = "user@example.com";
	        User user = new User();
	        Post expectedPost = new Post();
	        expectedPost.setContent(content);
	        expectedPost.setUser(user);
	        when(userRepository.findByEmail(email)).thenReturn(user);

	        // Create an ArgumentCaptor to capture the Post instance passed to postRepository.save()
	        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);

	        // Act
	        postService.createPost(content, email);

	        // Verify that userRepository.findByEmail(email) was called
	        verify(userRepository).findByEmail(email);
	        
	        // Capture the Post instance passed to postRepository.save() and perform assertions
	        verify(postRepository).save(postCaptor.capture());
	        Post savedPost = postCaptor.getValue();
	        assertEquals(content, savedPost.getContent());
	        assertEquals(user, savedPost.getUser());
	    }


}
