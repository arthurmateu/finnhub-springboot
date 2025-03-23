# finnhub-springboot
Spring Boot RESTful API consumer for Finnhub's Stock Symbols API endpoint. Features CRUD operations, dynamic filtering and pagination. 

### How to run
1. Input your [finnhub](https://finnhub.io/dashboard) token and what exchange you want to work with on `src/main/resources/application.properties` to populate the database.
2. Run the application with `./mvnw spring-boot:run`

### Documentation
Running this application you can check [Swagger UI](http://localhost:8080/swagger-ui/index.html) for documentation or [OpenAPI](http://localhost:8080/v3/api-docs)