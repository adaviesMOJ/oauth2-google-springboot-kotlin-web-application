# Oauth2 Learning Project

This repository follows in Kotlin the spring academy course https://spring.academy/courses/spring-academy-secure-rest-api-oauth2 for securing an API with spring security and Oauth2.
It will contain a fully working RESTful API which utilises the following tech:

- Spring boot
- Spring security (Oauth2)
- Posgresql database (and flyway to manage migrations)
- Docker and docker-compose (for building and running locally)

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


## Stage 3 - Adding Google as a login option for users
We've configured Google to be a working oauth2 client for our service. Users will now be able to registry with our service via Google. When accessing any endpoint in our service, spring will automatically redirect them to Google to login. On successful login our override of the DefaultOAuth2UserService (CustomOAuth2UserService) will capture the oauth2 user and their claims and construct a UserEntity to store in our users table.

The Google client was configured on https://console.cloud.google.com/ by creating a project, then going to API & Services and configuring the project. The outcome is a client_id and client_secret and redirect_url which we plugged into our application.yml.

A rework has also taken place to use the google oauth2 identifier (sub) instead of username, for now this is good enough for this learning project.

## Stage 4 - Adding basic UI
As a final step of this project, a very basic use of Thymeleas has been added to display a conditional navbar depending on the user being authenticated or not.

Next steps? -- Improve error handling, but for now this project has achieved what it set out to do - showcase oauth2 with Google login as an example.
