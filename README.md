PROJECT DETAILS

- The application stands up from port 9091
- Docker stands up from default port 8080
- H2 - in memory database was used. (In application.yml, the url can also be changed to mem, file, for persistence)
- Java, SpringBoot, Spring Data Jpa, Spring Security, Hibernate was used in the project
- Swagger added with auth - (All incoming requests(POST-PATCH-GET) are allowed and can be commented out when necessary)
- Token Validation has been made with Bearer Token
- When get tokens, either register with the signup endpoint and receive the token from the login endpoint. Or, it is taken from the password generator in the application, saved to the db and tokens are taken from the login endpoint
- Postman Collection has been created
- Unit tests were written with(JUNIT5 and MOCKITO) at Service and Controller levels
- Integration test(Auth) was written.
- Application has been dockerized.