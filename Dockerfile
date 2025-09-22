# ============================
# Stage 1: Build the JAR
# ============================
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy only pom.xml first to cache dependencies
COPY pom.xml .

# Download dependencies (for faster rebuilds)
RUN mvn dependency:go-offline -B

# Copy all source code
COPY src ./src

# Build the Spring Boot fat JAR
RUN mvn clean package -DskipTests

# ============================
# Stage 2: Create runtime image
# ============================
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render will override $PORT)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
