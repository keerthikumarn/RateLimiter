package com.api.rate.limiter.impl;

import com.api.rate.limiter.service.RateLimiterService;

/**
 * Entry point class for rate limiter service
 *
 */
public class App {
	public static void main(String[] args) {
		RateLimiterService rateLimiter = new SlidingWindowRateLimiter();
		rateLimiter.addUser("keerthi", 0, 15);
		System.out.println(rateLimiter.isRequestAllowed("keerthi"));
		for (int i = 0; i < 10; i++) {
			System.out.println(rateLimiter.isRequestAllowed("keerthi"));
		}
	}
}
