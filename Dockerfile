# Stage 1: Build application
FROM openjdk:17-jdk-alpine as builder

ARG ENV_NAME
ENV ENV_NAME=${ENV_NAME}

# Ensure gradle wrapper is runnable
WORKDIR /app
COPY gradlew .
COPY gradle gradle
RUN chmod +x ./gradlew

# Copy source code
COPY src src
COPY build.gradle .

# Compile and build application
# - Source is compiled during build time.
# - To reduce task wait time on container spin up during auto scaling, the following can be done:
# - Compile the jar file outside Docker.
# - Reference the compiled jar inside the Docker. This reduces the boot up time.
RUN ./gradlew bootJar

# Stage 2: Run application
FROM openjdk:17-jdk-alpine

LABEL maintainer="aarthi.kumar@soaringed.com"

VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Copy JAR from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar


# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]