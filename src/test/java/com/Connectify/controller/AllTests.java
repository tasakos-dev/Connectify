package com.Connectify.controller;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ ConnectifyControllerTest.class, FeedControllerTest.class, LoginTest.class })

public class AllTests {

}
