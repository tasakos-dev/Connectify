package com.Connectify.service;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CommentServiceTest.class, FollowerServiceTest.class, PostServiceTest.class, UserServiceTest.class })
public class AllTests {

}
