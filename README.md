# Getting Started
### Reference Documentation

## Description
This is a Spring Boot application that fetches music festivals data from a provided API and restructures the data. It groups bands under record labels, and lists the festivals they have played in.

## Prerequisites
- JDK 17
- An IDE (like IntelliJ IDEA)

## Building and Running

### Locally
- Clone this repository
- Inside the project directory, run this command from the terminal to build the app:
``` 
./mvnw clean install
```

## How to use 
- To run your application, use the following command:
```
./mvnw spring-boot:run
```

## API Endpoints
- `GET /api/mapData`: Fetches data about music festivals and groups them under record labels.

## Error Handling
The application is designed to handle errors gracefully and return user-friendly error messages.
