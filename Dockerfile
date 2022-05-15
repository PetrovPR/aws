FROM openjdk:8-jdk-alpine
COPY config.properties config.properties
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]