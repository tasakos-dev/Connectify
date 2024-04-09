package com.Connectify.entity;

import java.io.Serializable;

public class FollowerId implements Serializable {
    private Long follower;
    private Long following;
    
    public FollowerId() {
		// TODO Auto-generated constructor stub
	}
    
	public Long getFollower() {
		return follower;
	}
	public void setFollower(Long follower) {
		this.follower = follower;
	}
	public Long getFollowing() {
		return following;
	}
	public void setFollowing(Long following) {
		this.following = following;
	}

 
}
