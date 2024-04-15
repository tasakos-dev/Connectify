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
import com.Connectify.exception.MaxCommentException;
import com.Connectify.service.CommentService;
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
        String email = principal.getName();
        
        
        // Retrieve posts from people the user follows
        List<Post> followedPosts = postService.getFollowedPosts(email);
        
        // Retrieve the user's own posts
        List<Post> userPosts = postService.getUserPosts(email);
        
        // Retrieve the user's followers
        List<User> followers = followerService.getUserFollowers(email);
        
        // Add data to the model
        model.addAttribute("email", email);
        model.addAttribute("plan", userService.getPlan(email));
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
    	String email = principal.getName();
    	
        postService.createPost(content, email);
        // Redirect back to the feed page
        return "redirect:/feed";
    }
    
    @PostMapping("/comment")
    public String addComment(@RequestParam("postId") Long postId, @RequestParam("content") String content, 
    		@AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        String email = userDetails.getUsername();
        try {
        	commentService.addComment(email, postId, content);
		} catch (MaxCommentException e) {
			System.err.println(e.getMessage());
			redirectAttributes.addAttribute("errorMessage", e.getMessage());
		}
        
        return "redirect:/feed";
    }
    
    @PostMapping("/unfollow")
    public String unfollowUser(@RequestParam("followerId") Long followerId, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        followerService.unfollowUser(email, followerId);
        return "redirect:/feed";
    }

    @PostMapping("/follow")
    public String followUser(@RequestParam("email") String email, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        String myEmail = userDetails.getUsername();
        try {
        	followerService.followUser(myEmail, email);
			
		} catch (Exception e) {
			redirectAttributes.addAttribute("errorMessage", "User does not exist");
		}
        
        return "redirect:/feed";
    }

}