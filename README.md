# Ice-Cream Parlor: Menu Service

This is a Spring Boot microservice that implements the *Menu API* of the software.

## Some technologies used:
- Spring Boot, Spring Web, Spring JPA, Spring validation
- H2 in-memory database
- Lombok
- Records (Java 14)
- Lambdas, Streams, Optionals (Java 8)

## Additional points
- Tests are against the REST API directly
- Because the service is a simple CRUD, there are no unit tests and there is no separate service layer. These are needed if there's any business logic.
