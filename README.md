# Oauth2 Learning Project

This repository follows in Kotlin the spring academy course https://spring.academy/courses/spring-academy-secure-rest-api-oauth2 for securing an API with spring security and Oauth2.
It will contain a fully working RESTful API which utilises the following tech:

- Spring boot
- Spring security (Oauth2)
- Posgresql database (and flyway to manage migrations)
- Docker and docker-compose (for building and running locally)
- more... (eventually correct storing and configuring of env vars, deployment, swagger docs and more)

## Change Log

### Stage 1 - Initialising basic spring security
By adding the starter-security and security-test dependencies,
we enable HTTP BASIC authorisation. This secures all routes by default
and generates a default user and password to access endpoints.

It also handles response codes for unauthorised users for many things
like non-existing endpoints (401 instead of 404) etc...