package com.Connectify.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.Connectify.entity.Comment;
import com.Connectify.entity.Post;
import com.Connectify.exception.MaxComment;
import com.Connectify.repository.CommentRepository;
import com.Connectify.repository.PostRepository;

@Service
public class CommentServiceImpl extends FeedService implements CommentService{
	
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void addComment(String username, Long postId, String content) throws MaxComment{
    	Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Create the comment object
        Comment comment = new Comment();
        comment.setUser(userRepository.findByUsername(username)); // Assuming the user's username is used to identify the user
        comment.setContent(content);
        comment.setPost(post); // Associate the comment with the post
        comment.setCreatedAt(LocalDateTime.now());

        // Save the comment
        try {
        	commentRepository.save(comment);
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("ser has exceeded the maximum comment limit for this post")) {
				throw new MaxComment();
			}
		}       
    }
}
