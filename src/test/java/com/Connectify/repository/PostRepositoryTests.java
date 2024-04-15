package com.Connectify.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.Connectify.entity.Post;
import com.Connectify.entity.User;

@SpringBootTest
@TestPropertySource(
		 locations = "classpath:applicationTest.properties")
class PostRepositoryTests {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    
    @BeforeEach
    void initEach() {
        // Reset database state before each test
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void testFindByUserInOrderByCreatedAtDesc() {
        User user = new User();
        LocalDateTime now = LocalDateTime.now();
        
        user.setEmail("PostTesting@example.com");
        user.setPasswordHash("password");
        user.setPaidPlan(false);
        userRepository.save(user);

        Post post1 = new Post();
        post1.setUser(user);
        post1.setContent("Test post 1");
        post1.setCreatedAt(now);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setUser(user);
        post2.setContent("Test post 2");
        post2.setCreatedAt(now.plusSeconds(5));
        postRepository.save(post2);

        List<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(Collections.singletonList(user));

        assertEquals(2, posts.size());
        assertEquals("Test post 2", posts.get(0).getContent());
        assertEquals("Test post 1", posts.get(1).getContent());


        
    }

    @Test
    public void testFindByUserOrderByCreatedAtDesc() {
        User user = new User();

        LocalDateTime now = LocalDateTime.now();

        user.setEmail("PostTesting@example.com");
        user.setPasswordHash("password");
        userRepository.save(user);

        Post post1 = new Post();
        post1.setUser(user);
        post1.setContent("Test post 1");
        post1.setCreatedAt(now);
        postRepository.save(post1);

        Post post2 = new Post();
        post2.setUser(user);
        post2.setContent("Test post 2");
        post2.setCreatedAt(now.plusSeconds(5));
        postRepository.save(post2);

        List<Post> posts = postRepository.findByUserOrderByCreatedAtDesc(user);

        assertEquals(2, posts.size());
        assertEquals("Test post 2", posts.get(0).getContent());
        assertEquals("Test post 1", posts.get(1).getContent());
    }
    
}

