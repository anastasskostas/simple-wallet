FROM openjdk:8-jdk-alpine
MAINTAINER Tasos

EXPOSE 5050

# cd /app
WORKDIR /app

# copy jar
COPY build/libs/api.jar app.jar

# java -jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

