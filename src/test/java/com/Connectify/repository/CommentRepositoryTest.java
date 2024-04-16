package com.Connectify.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.dao.DataAccessException;
import org.springframework.test.context.TestPropertySource;

import com.Connectify.entity.Comment;
import com.Connectify.entity.Post;
import com.Connectify.entity.User;

@TestPropertySource(
		 locations = "classpath:applicationTest.properties")
@SpringBootTest
class CommentRepositoryTest {
	@Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void initEach() {
    	commentRepository.deleteAll();
    	postRepository.deleteAll();
    	userRepository.deleteAll();
        
        
    }
    
    @Test
    void testSaveComment() {
        // Create a user
        User user = new User();

        user.setEmail("commentUser@example.com");
        user.setPasswordHash("password");
        user.setPaidPlan(false);
        userRepository.save(user);

        // Create a post
        Post post = new Post();
        post.setUser(user);
        post.setContent("Test post");
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);

        // Create a comment
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent("Test comment");
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);

        // Retrieve the saved comment
        Comment savedComment = commentRepository.findById(comment.getCommentId()).orElse(null);

        // Verify that the comment was saved successfully
        assertNotNull(savedComment);
        assertEquals(comment.getContent(), savedComment.getContent());
        assertEquals(comment.getCreatedAt(), savedComment.getCreatedAt());

    }
    
    @Test
    void testSaveMultipleComments() {
        // Create a user
        User user = new User();
        user.setEmail("commentUser@example.com");
        user.setPaidPlan(false);
        user.setPasswordHash("password");
        userRepository.save(user);

        // Create a post
        Post post = new Post();
        post.setUser(user);
        post.setContent("Test post");
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);

        // Create 10 comments
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setPost(post);
            comment.setContent("Test comment " + i);
            comment.setCreatedAt(LocalDateTime.now());
            commentRepository.save(comment);
        }

        // Attempt to create an 11th comment
        Comment comment11 = new Comment();
        comment11.setUser(user);
        comment11.setPost(post);
        comment11.setContent("Test comment 11");
        comment11.setCreatedAt(LocalDateTime.now());
        
//        commentRepository.save(comment11);

        // Verify that an exception is thrown when trying to create the 11th comment
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            commentRepository.save(comment11);
        });

        // Verify the exception message
        exception.getRootCause();
        
        assertTrue(exception.getRootCause().getMessage().contains("User has exceeded the maximum comment limit for this post"));
    }
	
}
