FROM openjdk:17-jdk-alpine

LABEL maintainer="aarthi.kumar@soaringed.com"

VOLUME /tmp

ARG APPLICATION_FILE
ADD ${APPLICATION_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]