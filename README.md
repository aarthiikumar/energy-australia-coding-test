# Getting Started

## Description
This is a Spring Boot application that fetches music festivals data from a provided API and restructures the data. It groups bands under record labels, and lists the festivals they have played in.

## Prerequisites
- JDK 17
- An IDE (like IntelliJ IDEA)
- Docker

## Configuration

In this project, we have three main configurations for various environments:

1. **Development**: This is the environment for developers to build, test and debug the project.
2. **Staging**: This environment closely replicates the Production environment. It is used for final testing before deploying the product to Production.
3. **Production**: This environment is the live product, visible and accessible by the end-users.

   
## Building and Running

- Clone this repository

### Run with docker
- Build the Docker image from your Dockerfile:
```
docker build -t my-application .
```
- And run it:
```angular2html
docker run -e "ENV_NAME=${ENV_NAME}" -p 8080:8080 my-application
```
In the above command, we use Docker's -e option to pass the ENV_NAME environment variable to the Docker container

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
- `GET /api/musicRecords`: Fetches data about music festivals and groups them under record labels.

## Error Handling
The application is designed to handle errors gracefully and return user-friendly error messages.
