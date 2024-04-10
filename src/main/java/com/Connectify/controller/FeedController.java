package com.Connectify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Connectify.entity.Post;
import com.Connectify.entity.User;
import com.Connectify.service.FeedService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/feed")
public class FeedController {

	@Autowired
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public String showFeed(Model model, Principal principal) {
        String username = principal.getName();
        
        
        // Retrieve posts from people the user follows
        List<Post> followedPosts = feedService.getFollowedPosts(username);
        
        // Retrieve the user's own posts
        List<Post> userPosts = feedService.getUserPosts(username);
        
        // Retrieve the user's followers
        List<User> followers = feedService.getUserFollowers(username);
        
        // Add data to the model
        model.addAttribute("username", username);
        model.addAttribute("plan", feedService.getPlan(username));
        model.addAttribute("followedPosts", followedPosts);
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("followers", followers);
        
        // Return the feed page
        return "feed";
    }
    
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute("content") String content, Principal principal) {
    	System.err.println("hello");
    	String username = principal.getName();
    	
        feedService.createPost(content, username);
        // Redirect back to the feed page
        return "redirect:/feed";
    }
    
    @PostMapping("/comment")
    public String addComment(@RequestParam("postId") Long postId, @RequestParam("content") String content, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        feedService.addComment(username, postId, content);
        return "redirect:/feed";
    }
}