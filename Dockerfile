# Use OpenJDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first to leverage caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy project source
COPY src src

# Build jar
RUN ./mvnw package -DskipTests

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","target/kopa-backend-0.0.1-SNAPSHOT.jar"]
