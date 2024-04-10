package com.Connectify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Connectify.entity.Follower;
import com.Connectify.entity.User;

import java.util.*;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findByFollower(User follower);
    List<Follower> findByFollowing(User following);
    List<Follower> findFollowingByFollower(User follower);
    List<Follower> findFollowersByFollowing(User user);

}