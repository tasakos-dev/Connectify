package com.Connectify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.Connectify.entity.Post;
import com.Connectify.entity.User;

import java.util.*;

/**
 * Repository interface for managing post data. 
 * @author tasakos
 *
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	/**
     * Find posts created by any user in the provided list, ordered by creation time descending.
     *
     * @param users The list of users whose posts are to be retrieved.
     * @return A list of posts created by any user in the provided list, ordered by creation time descending.
     */
    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);
    
    /**
     * Find posts created by a specific user, ordered by creation time descending.
     *
     * @param user The user whose posts are to be retrieved.
     * @return A list of posts created by the specified user, ordered by creation time descending.
     */
    List<Post> findByUserOrderByCreatedAtDesc(User user);
}
