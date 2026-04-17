# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Copy the pom.xml and src code
COPY pom.xml .
COPY src ./src
# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
