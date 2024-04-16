package com.Connectify.controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Connectify.entity.Post;
import com.Connectify.entity.User;
import com.Connectify.exception.MaxCommentException;
import com.Connectify.exception.UserNotExistsException;
import com.Connectify.service.CommentService;
import com.Connectify.service.FollowerService;
import com.Connectify.service.PostService;
import com.Connectify.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class FeedControllerTest {

	 	@Autowired
	    private WebApplicationContext context;

	    @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private PostService postService;

	    @MockBean
	    private FollowerService followerService;

	    @MockBean
	    private UserService userService;

	    @MockBean
	    private CommentService commentService;
	    
	    
	    

	    @BeforeEach
	    public void setup() {
	        mockMvc = MockMvcBuilders
	                .webAppContextSetup(context)
	                .build();
	    }

	    @Test
	    @WithMockUser(username = "test@example.com")
	    void testShowFeedReturnsPage() throws Exception {
	        List<Post> followedPosts = new ArrayList<>();
	        List<Post> userPosts = new ArrayList<>();
	        List<User> followers = new ArrayList<>();
	        when(postService.getFollowedPosts(Mockito.anyString())).thenReturn(followedPosts);
	        when(postService.getUserPosts(Mockito.anyString())).thenReturn(userPosts);
	        when(followerService.getUserFollowers(Mockito.anyString())).thenReturn(followers);
	        when(userService.getPlan(Mockito.anyString())).thenReturn("free");

	        mockMvc.perform(MockMvcRequestBuilders.get("/feed").principal(new Principal() {
	            @Override
	            public String getName() {
	                return "test@example.com";
	            }
	        }))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.view().name("feed"))
	                .andExpect(MockMvcResultMatchers.model().attributeExists("email"))
	                .andExpect(MockMvcResultMatchers.model().attributeExists("plan"))
	                .andExpect(MockMvcResultMatchers.model().attributeExists("followedPosts"))
	                .andExpect(MockMvcResultMatchers.model().attributeExists("userPosts"))
	                .andExpect(MockMvcResultMatchers.model().attributeExists("followers"));
	    }
	    
	    @Test
	    @WithMockUser(username = "test@example.com")
	    void testCreatePost() throws Exception {
	        String content = "Test post content";
	        mockMvc.perform(MockMvcRequestBuilders.post("/feed/createPost")
	                .principal(new Principal() {
	                    @Override
	                    public String getName() {
	                        return "test@example.com";
	                    }
	                })
	                .param("content", content))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed"));

	    }
	    
	    @Test
	    @WithMockUser(username = "test@example.com")
	    void testAddComment() throws Exception {
	        Long postId = 1L;
	        String content = "Test comment content";
	    	doNothing().when(commentService).addComment("test@example.com", postId, content);
	        mockMvc.perform(MockMvcRequestBuilders.post("/feed/comment")
	        		.principal(new Principal() {
	                    @Override
	                    public String getName() {
	                        return "test@example.com";
	                    }
	                })
	                .param("postId", String.valueOf(postId))
	                .param("content", content))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed"));
	    }
	    
	    @Test
	    @WithMockUser(username = "test@example.com")
	    void testAddCommentMaxComments() throws Exception {
	        Long postId = 1L;
	        String content = "Test comment content";
	    	doThrow(MaxCommentException.class).when(commentService).addComment("test@example.com", postId, content);
	        mockMvc.perform(MockMvcRequestBuilders.post("/feed/comment")
	        		.principal(new Principal() {
	                    @Override
	                    public String getName() {
	                        return "test@example.com";
	                    }
	                })
	                .param("postId", String.valueOf(postId))
	                .param("content", content))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed?errorMessage=User+has+exceeded+the+maximum+comment+limit+for+this+post"));
	    }

	    @Test
	    @WithMockUser(username = "test@example.com")
	    void testUnfollowUser() throws Exception {
	        Long followerId = 1L;
	        mockMvc.perform(MockMvcRequestBuilders.post("/feed/unfollow")
	        		.principal(new Principal() {
	                    @Override
	                    public String getName() {
	                        return "test@example.com";
	                    }
	                })
	                .param("followerId", String.valueOf(followerId)))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed"));

	    }

	    @Test
	    @WithMockUser(username = "test@example.com")
	    void testFollowUserUserExists() throws Exception {
	        doNothing().when(followerService).followUser("test@example.com", "user@example.com");
	        mockMvc.perform(MockMvcRequestBuilders.post("/feed/follow")
	                .principal(() -> "test@example.com")
	                .param("email", "user@example.com"))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed"));
	    }
	    
	    @Test
	    @WithMockUser(username = "test@example.com")
	    void testFollowUserUserNotExists() throws Exception {
	        String email = "user@example.com";
	        doThrow(UserNotExistsException.class).when(followerService).followUser("test@example.com", "user@example.com");
	        mockMvc.perform(MockMvcRequestBuilders.post("/feed/follow")
	        		.principal(new Principal() {
	                    @Override
	                    public String getName() {
	                        return "test@example.com";
	                    }
	                })
	                .param("email", email))
	                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
	                .andExpect(MockMvcResultMatchers.redirectedUrl("/feed?errorMessage=User+does+not+exist"));

	    }


}
