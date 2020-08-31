package com.api.rate.limiter.service;

/**
 * 
 * @author kenarayan
 *
 */
/**
 * Interface for the rate limit service
 * @author kenarayan
 *
 */
public interface RateLimiterService {
	/* To add a new user along with the no. of requests configured */
	void addUser(String userId, int noOfRequests, int timeInSeconds);

	/* To remove an user */
	void removeUser(String userId);

	/* To check whether the request made by the user is allowed */
	boolean isRequestAllowed(String userId);
}
