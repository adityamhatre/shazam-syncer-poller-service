FROM openjdk:8-jre-alpine
COPY build/libs/poller-1.0.0.jar /poller.jar
CMD ["/usr/bin/java","-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n", "-jar", "/poller.jar"]
