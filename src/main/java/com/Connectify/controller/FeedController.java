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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Connectify.entity.Post;
import com.Connectify.entity.User;
import com.Connectify.service.CommentService;
import com.Connectify.service.FeedService;
import com.Connectify.service.FollowerService;
import com.Connectify.service.PostService;
import com.Connectify.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/feed")
public class FeedController {

	
    private final PostService postService;
    private final FollowerService followerService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public FeedController(PostService postService, FollowerService followerService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.followerService = followerService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping
    public String showFeed(Model model, Principal principal, @RequestParam(value = "errorMessage", required = false) String errorMessage) {
        String username = principal.getName();
        
        
        // Retrieve posts from people the user follows
        List<Post> followedPosts = postService.getFollowedPosts(username);
        
        // Retrieve the user's own posts
        List<Post> userPosts = postService.getUserPosts(username);
        
        // Retrieve the user's followers
        List<User> followers = followerService.getUserFollowers(username);
        
        // Add data to the model
        model.addAttribute("username", username);
        model.addAttribute("plan", userService.getPlan(username));
        model.addAttribute("followedPosts", followedPosts);
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("followers", followers);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
        
        // Return the feed page
        return "feed";
    }
    
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute("content") String content, Principal principal) {
    	System.err.println("hello");
    	String username = principal.getName();
    	
        postService.createPost(content, username);
        // Redirect back to the feed page
        return "redirect:/feed";
    }
    
    @PostMapping("/comment")
    public String addComment(@RequestParam("postId") Long postId, @RequestParam("content") String content, 
    		@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        String username = userDetails.getUsername();
        try {
        	commentService.addComment(username, postId, content);
		} catch (Exception e) {
			System.err.println("hello");
			redirectAttributes.addAttribute("errorMessage", e.getMessage());
		}
        
        return "redirect:/feed";
    }
    
    @PostMapping("/unfollow")
    public String unfollowUser(@RequestParam("followerId") Long followerId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        followerService.unfollowUser(username, followerId);
        return "redirect:/feed";
    }

    @PostMapping("/follow")
    public String followUser(@RequestParam("email") String email, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        String username = userDetails.getUsername();
        try {
        	followerService.followUser(username, email);
			
		} catch (Exception e) {
			redirectAttributes.addAttribute("errorMessage", "User does not exist");
		}
        
        return "redirect:/feed";
    }

}