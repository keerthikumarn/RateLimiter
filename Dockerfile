FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/RateLimiter.jar

WORKDIR /Rate-Limiter

COPY ${JAR_FILE} RateLimter.jar

ENTRYPOINT ["java","-jar","RateLimter.jar"]