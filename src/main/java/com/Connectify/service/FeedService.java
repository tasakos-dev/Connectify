package com.Connectify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Connectify.entity.Comment;
import com.Connectify.entity.Follower;
import com.Connectify.entity.Post;
import com.Connectify.entity.User;
import com.Connectify.repository.CommentRepository;
import com.Connectify.repository.FollowerRepository;
import com.Connectify.repository.PostRepository;
import com.Connectify.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class FeedService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FollowerRepository followerRepository;

    @Autowired
    public FeedService(PostRepository postRepository, UserRepository userRepository, FollowerRepository followerRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followerRepository = followerRepository;
        this.commentRepository = commentRepository;
    }

    // Method to retrieve posts from people the user follows
    public List<Post> getFollowedPosts(String username) {
        User user = userRepository.findByUsername(username);
        List<User> followingUsers = new ArrayList<>();
        for (Follower following : followerRepository.findFollowingByFollower(user)) {
        	followingUsers.add(following.getFollowing());
        }
        List<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(followingUsers);
        posts.forEach(post -> Collections.reverse(post.getComments()));
        
        return posts;
    }

    // Method to retrieve the user's own posts
    public List<Post> getUserPosts(String username) {
        User user = userRepository.findByUsername(username);
        
        List<Post> posts = postRepository.findByUserOrderByCreatedAtDesc(user);
        
        posts.forEach(post -> Collections.reverse(post.getComments()));
        return posts;
    }

    // Method to retrieve the user's followers
    public List<User> getUserFollowers(String username) {
    	List<User> followers = new ArrayList();
        User user = userRepository.findByUsername(username);
        for (Follower follower: followerRepository.findFollowersByFollowing(user)) {
        	followers.add(follower.getFollower());
        }
        	
        return followers;
    }
    public void createPost(String content, String username) {
        // Create a new post
        Post post = new Post();
        post.setContent(content);
        post.setUser(userRepository.findByUsername(username));
        post.setCreatedAt(LocalDateTime.now());
        
        // Save the post to the database
        postRepository.save(post);
    }
    
    public void addComment(String username, Long postId, String content) {
    	Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // Create the comment object
        Comment comment = new Comment();
        comment.setUser(userRepository.findByUsername(username)); // Assuming the user's username is used to identify the user
        comment.setContent(content);
        comment.setPost(post); // Associate the comment with the post
        comment.setCreatedAt(LocalDateTime.now());

        // Save the comment
        commentRepository.save(comment);
    }
    
    public String getPlan(String username) {
    	return userRepository.findByUsername(username).isPaidPlan() ? "Paid" : "Free" ;
    }
}

