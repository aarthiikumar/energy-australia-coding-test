# Getting Started

## Description
This is a Spring Boot application that fetches music festivals data from a provided API and restructures the data. It groups bands under record labels, and lists the festivals they have played in.

## Prerequisites
- JDK 17
- An IDE (like IntelliJ IDEA)

## Building and Running

- Clone this repository

### Run with docker
- Build the Docker image from your Dockerfile:
```
docker build -t my-application .
```
- And run it:
```angular2html
docker run -p 8080:8080 my-application
```

### Run with gradle
- Inside the project directory, run this command from the terminal to build the app:
``` 
./gradlew build
```

- To run your application, use the following command:
```
./gradlew bootRun
```

## API Endpoints
- `GET /api/mapData`: Fetches data about music festivals and groups them under record labels.

## Error Handling
The application is designed to handle errors gracefully and return user-friendly error messages.
