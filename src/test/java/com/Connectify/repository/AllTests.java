package com.Connectify.repository;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CommentRepositoryTest.class, FollowerRepositoryTest.class, PostRepositoryTest.class,
		UserRepositoryTest.class })
public class AllTests {

}
