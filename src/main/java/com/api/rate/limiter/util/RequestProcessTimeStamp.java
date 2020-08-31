package com.api.rate.limiter.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Class for managing the details of the time stamp of every request processed
 * @author kenarayan
 *
 */
public class RequestProcessTimeStamp {

	private Queue<Long> timestamps;
	private int noOfRequests;
	private int timeInSeconds;

	public RequestProcessTimeStamp(int noOfRequests, int timeInSeconds) {
		super();
		this.timestamps = new ConcurrentLinkedDeque<Long>();
		this.noOfRequests = noOfRequests;
		this.timeInSeconds = timeInSeconds;
	}

	/**
	 * To clear the previous/old time stamps from a request for a given point of time
	 * @param currentTimestamp
	 */
	public void clearOldTimeStamps(long currentTimestamp) {
		while (timestamps.size() != 0 && (currentTimestamp - timestamps.peek()) > timeInSeconds) {
			timestamps.remove();

		}
	}

	/* getXXX() and setXXX()*/
	public Queue<Long> getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(Queue<Long> timestamps) {
		this.timestamps = timestamps;
	}

	public int getRequests() {
		return noOfRequests;
	}

	public void setRequests(int noOfRequests) {
		this.noOfRequests = noOfRequests;
	}

	public int getWindowTimeInSec() {
		return timeInSeconds;
	}

	public void setWindowTimeInSec(int timeInSeconds) {
		this.timeInSeconds = timeInSeconds;
	}

}
