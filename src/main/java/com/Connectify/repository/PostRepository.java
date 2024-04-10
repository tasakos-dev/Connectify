package com.Connectify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.Connectify.entity.Post;
import com.Connectify.entity.User;

import java.util.*;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);
    List<Post> findByUserInOrderByCreatedAtAsc(List<User> users);
    List<Post> findByUserOrderByCreatedAtDesc(User user);


}
