package com.api.rate.limiter.ApiRateLimiter;

import com.api.rate.limiter.impl.SlidingWindowRateLimiter;
import com.api.rate.limiter.service.RateLimiterService;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestSlidingWindowRateLimiter extends TestCase {

	private RateLimiterService rateLimiter;
	private static final String USER_ID = "Keerthi";
	private static final int NO_OF_REQUESTS = 15;
	private static final int NO_OF_REQ_PROCESSED = 10;
	private static final int CUSTOM_REQ_LIMIT_EXCEED = 20;

	public void setUp() {
		rateLimiter = new SlidingWindowRateLimiter();
	}

	public void testSlidingWindowRateLimitAddUser() {
		rateLimiter.addUser(USER_ID, NO_OF_REQUESTS, NO_OF_REQ_PROCESSED);
		Assert.assertTrue(rateLimiter.isRequestAllowed(USER_ID));
	}

	public void testSlidingWindowRateLimitWithDenial() {
		rateLimiter.addUser(USER_ID, NO_OF_REQUESTS, NO_OF_REQ_PROCESSED);
		rateLimiter.isRequestAllowed(USER_ID);
		rateLimiter.removeUser(USER_ID);
		Assert.assertFalse(rateLimiter.isRequestAllowed(USER_ID));
	}

	public void testSlidingWindowRateLimitAddUserWithoutDenial() {
		rateLimiter.addUser(USER_ID, NO_OF_REQUESTS, NO_OF_REQ_PROCESSED);
		for (int i = 0; i < NO_OF_REQUESTS; i++) {
			rateLimiter.isRequestAllowed(USER_ID);
		}
		Assert.assertFalse(rateLimiter.isRequestAllowed(USER_ID));
	}

	public void testSlidingWindowRateLimitAddUserWithDenial() {
		rateLimiter.addUser(USER_ID, NO_OF_REQUESTS, NO_OF_REQ_PROCESSED);
		for (int i = 0; i < CUSTOM_REQ_LIMIT_EXCEED; i++) {
			rateLimiter.isRequestAllowed(USER_ID);
		}
		Assert.assertFalse(rateLimiter.isRequestAllowed(USER_ID));
	}

	public void testSlidingWindowRateLimitWithNoRequests() {
		rateLimiter.addUser(USER_ID, 0, NO_OF_REQ_PROCESSED);
		Assert.assertFalse(rateLimiter.isRequestAllowed(USER_ID));
	}
}
