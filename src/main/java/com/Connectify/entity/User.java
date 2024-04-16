package com.Connectify.entity;



import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;
    
    @Column(nullable = false)
    private boolean paidPlan;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public boolean isPaidPlan() {
		return paidPlan;
	}

	public void setPaidPlan(boolean paid_plan) {
		this.paidPlan = paid_plan;
	}

	
    
}
