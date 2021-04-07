FROM openjdk:17-jdk-alpine3.13
MAINTAINER lakhanpriyanka25@gmail.com
RUN ls -l
COPY build/libs/*.jar /application.jar
ENTRYPOINT ["java","-jar","/application.jar"]