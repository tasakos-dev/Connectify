package com.Connectify.service;

import org.springframework.stereotype.Service;

import com.Connectify.exception.MaxCommentException;

/**
 * Service interface for managing comments.
 * @author tasakos
 *
 */
@Service
public interface CommentService {

	/**
     * Adds a comment to a post.
     *
     * @param email the email of the user adding the comment
     * @param postId   the ID of the post to which the comment is being added
     * @param content  the content of the comment
     * @throws MaxCommentException if the maximum number of comments for the post has been reached
     */
	public void addComment(String email, Long postId, String content) throws MaxCommentException;
}
