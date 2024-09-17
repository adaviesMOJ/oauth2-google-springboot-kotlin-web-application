# Stage 1: Build the application
FROM gradle:7.6-jdk17 AS build

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Download dependencies to cache them
RUN ./gradlew build --no-daemon || return 0

# Copy source code and build the app
COPY src ./src
RUN ./gradlew build --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=build /app/build/libs/oauth-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 3939

ENTRYPOINT ["java", "-jar", "app.jar"]