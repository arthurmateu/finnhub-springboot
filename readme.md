# finnhub-springboot
Spring Boot RESTful API consumer with basic CRUD operations

1. Start a PostgreSQL database with `docker-compose up -d`
2. Input your [finnhub](https://finnhub.io/dashboard) token and what exchange you want to work with on `application.properties`.
3. Run the application with `./mvnw spring-boot:run`

### Documentation
Running this application you can check [Swagger UI](http://localhost:8080/swagger-ui/index.html) for documentation or [OpenAPI](http://localhost:8080/v3/api-docs)
