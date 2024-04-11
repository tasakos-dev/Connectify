package com.Connectify.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Connectify.entity.Post;

@Service
public interface PostService {
	public List<Post> getFollowedPosts(String username);
	public List<Post> getUserPosts(String username);
	public void createPost(String content, String username);
}
