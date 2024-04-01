# Stage 1: Build application
FROM openjdk:17-jdk-alpine as builder

# Ensure gradle wrapper is runnable
WORKDIR /app
COPY gradlew .
COPY gradle gradle
RUN chmod +x ./gradlew

# Copy source code
COPY src src
COPY build.gradle .

# Compile and build application
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