package com.Connectify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Connectify.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
	public User findByEmail(String email);
	
}
