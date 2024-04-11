package com.Connectify.service;

import org.springframework.stereotype.Service;

import com.Connectify.exception.MaxComment;

@Service
public interface CommentService {

	public void addComment(String username, Long postId, String content) throws MaxComment;
}
