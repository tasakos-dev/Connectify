package com.Connectify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Connectify.entity.User;

/**
 * Repository interface for managing user data.
 * @author tasakos
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
	/**
     * Find a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return The user with the specified email address, if found; otherwise, null.
     */
	public User findByEmail(String email);
	
}
