# API Rate Limiter Service 

Rate Limiting is a critical component of API product's stability. API owners typically measure processing limits in Transactions Per Second (TPS). Some systems may have physical limitations on data transference.

### Definition

"To prevent an API from being overwhelmed, API owners often enforce a limit on the number of requests, or the quantity of data clients can consume"

### Types of Rate Limit 
    a. User Rate Limiting
    b. Geographic Rate Limiting
    c. Server Rate Limiting
    
### Sliding Window Log Algorithm
As part of this assignment, I opted sliding window log algorithm to implement the rate limit api.
Sliding Log rate limiting involves tracking a time stamped log for each user’s request. These logs are usually stored in a hash table or table that is sorted by time at which user hit the request to access the given API(s). Logs/requests with timestamps beyond a threshold limite are discarded.

a. For every user, a queue/sorted set of timestamps representing the times at which all the historical calls have made within the timespan of recent most window is maintained.

b. When ever a new request occurs, a check is performed for any timestamps older than the window time and these are deleted as they are no longer relevant.

c. The new timestamp is appended to the user’s request queue.

d. If the number of elements in the queue is not more than that of the allowed threshold limit, the request is let through, else an exception message is shown back to the user.

### Advantages of Rate Limiting

a. Manage policies and quotas
b. Prevention of resource starvation
c. Avoiding excess cost
d. Flow control

### Installation and Usage

    a. git clone git@github.com:keerthikumarn/RateLimiter.git
    b. navigate to RateLimiter root folder and perform "mvn clean install"
    c. Run App.java
    d. To run junits, run the command "mvn test"
    
### To run as a docker application
    a. Navigate to RateLimiter project root folder
    b. Execute the Docker file with the command "docker build -t ."
    c. docker ps -a
    d. Get the corresponding image id and execute the command "docker run <image_id>"

This tool can be integrated with the spring boot project (microservices) as a service to manage the API calls easily. Just add the RateLimiter.jar file into the corresponding project and perform the below steps:

    a. For any service instantiate the SlidingWindowRateLimiter as shown below:
    RateLimiterService rateLimiter = new SlidingWindowRateLimiter();
    
    b. Add the user who is requesting for the API access with no. of requests the API can allowed and time for the same in seconds.
    rateLimiter.addUser("test_user", 10, 15);
    
    c. Then invoke the main algo implemention as shown below from the rateLimiter:
    rateLimiter.isRequestAllowed("test_user")
    
As per the algo implementation, if the user is allowed, then the request will be processed, if not then a message will be sent back to the user stating the request cannot be processed at the given point of time.

