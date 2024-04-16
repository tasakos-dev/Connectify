package com.Connectify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Connectify.entity.Post;

/**
 * Service interface for managing posts.
 * @author tasakos
 *
 */
@Service
public interface PostService {
	
	/**
     * Retrieves the posts followed by a user.
     *
     * @param username the username of the user
     * @return a list of posts followed by the user
     */
	public List<Post> getFollowedPosts(String username);
	
	/**
     * Retrieves the posts created by a user.
     *
     * @param username the username of the user
     * @return a list of posts created by the user
     */
	public List<Post> getUserPosts(String username);
	
	/**
     * Creates a new post.
     *
     * @param content  the content of the post
     * @param username the username of the user creating the post
     */
	public void createPost(String content, String username);
}
