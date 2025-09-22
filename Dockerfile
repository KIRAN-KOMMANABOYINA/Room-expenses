
# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from target folder into the container
COPY target/*.jar app.jar

# Expose port (Spring Boot default is 8080, but Render will override with $PORT)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
