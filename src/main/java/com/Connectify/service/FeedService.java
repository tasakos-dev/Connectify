package com.Connectify.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.Connectify.entity.Comment;
import com.Connectify.entity.Follower;

import com.Connectify.entity.Post;
import com.Connectify.entity.User;
import com.Connectify.exception.MaxCommentException;
import com.Connectify.exception.UserNotExistsException;
import com.Connectify.repository.CommentRepository;
import com.Connectify.repository.FollowerRepository;
import com.Connectify.repository.PostRepository;
import com.Connectify.repository.UserRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedService{


	
    protected UserRepository userRepository;
    
    @Autowired
    public FeedService(UserRepository userRepository) {
    	this.userRepository = userRepository;
	}

}

