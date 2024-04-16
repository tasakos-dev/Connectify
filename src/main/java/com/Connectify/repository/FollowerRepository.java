package com.Connectify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Connectify.entity.Follower;
import com.Connectify.entity.User;

import java.util.*;

import javax.transaction.Transactional;

/**
 *  Repository interface for managing follower relationships.
 * @author tasakos
 *
 */
@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
	
	 /**
     * Find all followers of a specific user.
     *
     * @param user The user for whom to find followers.
     * @return A list of followers of the specified user.
     */
    List<Follower> findFollowingByFollower(User follower);
    
    /**
     * Find all users followed by a specific user.
     *
     * @param follower The user who is following.
     * @return A list of users followed by the specified user.
     */
    List<Follower> findFollowersByFollowing(User user);
    
    /**
     * Delete a follower relationship between two users.
     *
     * @param follower The user who is following.
     * @param following The user who is being followed.
     */
    @Transactional
    void deleteByFollowerAndFollowing(User follower, User following);

}