package com.Connectify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Connectify.entity.Comment;

/**
 * Repository interface for managing comments.
 * @author tasakos
 *
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    
}
