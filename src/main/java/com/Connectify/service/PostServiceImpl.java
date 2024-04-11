package com.Connectify.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Connectify.entity.Comment;
import com.Connectify.entity.Follower;
import com.Connectify.entity.Post;
import com.Connectify.entity.User;
import com.Connectify.repository.FollowerRepository;
import com.Connectify.repository.PostRepository;

@Service
public class PostServiceImpl extends FeedService implements PostService{
	private final PostRepository postRepository;
	private final FollowerRepository followerRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, FollowerRepository followerRepository) {
    	super();
        this.postRepository = postRepository;
        this.followerRepository = followerRepository;
    }

    // Method to retrieve posts from people the user follows
    @Override
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
    @Override
    public List<Post> getUserPosts(String username) {
        User user = userRepository.findByUsername(username);
        
        List<Post> posts = postRepository.findByUserOrderByCreatedAtDesc(user);
        
        posts.forEach(post -> {
            List<Comment> reversedComments = post.getComments().stream()
                    .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                    .collect(Collectors.toList());
            post.setComments(reversedComments);
            
        });
        
        return posts;
    }


    @Override
    public void createPost(String content, String username) {
        // Create a new post
        Post post = new Post();
        post.setContent(content);
        post.setUser(userRepository.findByUsername(username));
        post.setCreatedAt(LocalDateTime.now());
        
        // Save the post to the database
        postRepository.save(post);
    }

}
