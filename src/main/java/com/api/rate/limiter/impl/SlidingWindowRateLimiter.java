package com.api.rate.limiter.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.rate.limiter.service.RateLimiterService;
import com.api.rate.limiter.util.RequestProcessTimeStamp;

/**
 * Rate Limit service implementation with the usage of Sliding Window log
 * algorithm
 * 
 * @author kenarayan
 *
 */
public class SlidingWindowRateLimiter implements RateLimiterService {

	private static final Logger logger = LoggerFactory.getLogger(SlidingWindowRateLimiter.class);
	private Map<String, RequestProcessTimeStamp> rateLimitMap = null;

	/**
	 * Constructor to initialize the rateLimit map object
	 */
	public SlidingWindowRateLimiter() {
		logger.info("Calling the SlidingWindowRateLimiter() constructor and initializing the rateLimitMap object");
		rateLimitMap = new HashMap<String, RequestProcessTimeStamp>();
	}

	/**
	 * To add every user to the request to a memory (HashMap in this case) to
	 * register along with the noOfRequests and requests to be processed in a
	 * given point of time for a given API/service
	 */
	public void addUser(String userId, int noOfRequests, int timeInSeconds) {
		if(noOfRequests <= 0) {
			logger.debug("Atleast one request from the user is expected !! "+noOfRequests);
			return;
		}
		logger.debug("Adding the user : " + userId + " with no. of requests : " + noOfRequests);
		if (!rateLimitMap.containsKey(userId)) {
			rateLimitMap.put(userId, new RequestProcessTimeStamp(noOfRequests, timeInSeconds));
		}
	}

	/**
	 * To remove the user from the memory (HashMap in this case)
	 */
	public void removeUser(String userId) {
		if (rateLimitMap.containsKey(userId)) {
			rateLimitMap.remove(userId);
		}
	}

	/**
	 * To check whether every process requested by a given user at a given time
	 * can be processed or not. This implementation is based on the sliding
	 * window log algorithm which is based on the timestamp of the service
	 * request.
	 * 
	 */
	public boolean isRequestAllowed(String userId) {
		if (!rateLimitMap.containsKey(userId)) {
			logger.info(
					"User : " + userId + " is not allowed to get this request processed. Kindly try after some time");
			return false;
		}
		RequestProcessTimeStamp timeStamp = rateLimitMap.get(userId);
		synchronized (timeStamp) {
			long currTime = System.currentTimeMillis();
			timeStamp.clearOldTimeStamps(currTime);
			timeStamp.getTimestamps().add(currTime);
			if ((timeStamp.getTimestamps().size()) > timeStamp.getRequests()) {
				logger.debug("Dear : " + userId
						+ ", you have exceeded max no. of requests at this time. Kindly try after some time");
				return false;
			}
		}
		return true;
	}

}
