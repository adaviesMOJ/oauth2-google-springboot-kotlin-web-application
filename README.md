# Oauth2 Learning Project

This repository follows in Kotlin the spring academy course https://spring.academy/courses/spring-academy-secure-rest-api-oauth2 for securing an API with spring security and Oauth2.
It will contain a fully working RESTful API which utilises the following tech:

- Spring boot
- Spring security (Oauth2)
- Posgresql database (and flyway to manage migrations)
- Docker and docker-compose (for building and running locally)
- more... (eventually correct storing and configuring of env vars, deployment, swagger docs and more)

## Running Application
1. run the database container using command 
`docker-compose -f docker-compose-db.yml up -d`.
2. In intellij, run the spring boot application using default run configuration.
3. Navigate to `localhost:3939/`.

## Change Log

### Stage 1 - Initialising basic spring security
By adding the starter-security and security-test dependencies,
we enable HTTP BASIC authorisation. This secures all routes by default
and generates a default user and password to access endpoints.

It also handles response codes for unauthorised users for many things
like non-existing endpoints (401 instead of 404) etc...

### Stage 2 - Adding more complex spring security with Bearer Authentication
By adding the starter-oauth2-resource-server dependency, we tell spring to override 
the HTTP Basic authentication with Bearer Authentication. We then add configuration 
to the application.yml to tell spring boot where to authenticate any JWT that is passed in.

As this is early on, we use a public-key-location, but in next stages we'll change this 
to use a real oauth2 issuer (E.g. Google).

We finally restructured the CashCardController to no longer manually pass in user information 
and instead we now read it from the SecurityContextHolder using @CurrentSecurityContext and 
our custom annotation to wrap this code @CurrentOwner.

More reworking of the code has taken place along with these changes, to ensure tables have correct 
relationships and tests make use of specific WithMockUser to simulate real life usage.
