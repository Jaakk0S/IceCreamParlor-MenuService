# Ice-Cream Parlor: Menu Service

This Spring Boot microservice implements the *Menu API* of Ice-Cream Parlor demo app.

## Technologies used:
- Spring Boot, Spring Web, Spring JPA, Spring validation
- H2 in-memory database
- Lombok
- Records (Java 14)
- Lambdas, Streams, Optionals (Java 8)

## Running Tests

Tests are integration tests for the REST API and service layer. They are run using an in-memory H2 database.

Run

```
./mvnw clean test
```

## Building

Run

```
./build
```

or

```
./build omit_tests
```

This will produce the Docker image *icecreamparlor-menuservice*.

