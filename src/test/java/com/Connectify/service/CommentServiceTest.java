package com.Connectify.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import com.Connectify.entity.Comment;
import com.Connectify.entity.Post;
import com.Connectify.entity.User;
import com.Connectify.exception.MaxCommentException;
import com.Connectify.repository.CommentRepository;
import com.Connectify.repository.PostRepository;
import com.Connectify.repository.UserRepository;

@SpringBootTest
class CommentServiceTest {
	
	@Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;
    
    @Autowired
    private CommentService commentService;

   @BeforeEach
    void setUp() {
//        MockitoAnnotations.initMocks(this);
        commentService = new CommentServiceImpl(commentRepository, postRepository, userRepository);
    }

    @Test
    void testAddComment_Success() throws MaxCommentException {
        // Arrange
        Long postId = 1L;
        String email = "user@example.com";
        String content = "This is a comment";
        Post post = new Post();
        post.setPostId(postId);
        User user = new User();
        user.setEmail(email);

        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(commentRepository.save(any())).thenReturn(new Comment());
//        

        // Act
        assertDoesNotThrow(() -> commentService.addComment(email, postId, content));

        // Assert
        verify(postRepository, times(1)).findById(postId);
        verify(userRepository, times(1)).findByEmail(email);
        verify(commentRepository, times(1)).save(any());
    }

    @Test
    void testAddComment_MaxCommentException() {
        // Arrange
        Long postId = 1L;
        String email = "user@example.com";
        String content = "This is a comment";
        Post post = new Post();
        post.setPostId(postId);
        User user = new User();
        user.setEmail(email);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        RuntimeException rootCause = new RuntimeException("User has exceeded the maximum comment limit for this post");


        when(commentRepository.save(any())).thenThrow(new DataAccessException("Error saving comment", rootCause) {});

        // Act and Assert
        assertThrows(MaxCommentException.class, () -> commentService.addComment(email, postId, content));
    }



}
