package com.Connectify.repository;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CommentRepositoryTests.class, FollowerRepositoryTests.class, PostRepositoryTests.class,
		UserRepositoryTests.class })
public class AllTests {

}
