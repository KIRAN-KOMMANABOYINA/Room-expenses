# Use OpenJDK 17
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar into container
COPY target/*.jar app.jar

# Expose app port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
