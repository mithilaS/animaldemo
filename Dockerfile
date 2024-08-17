# Use the official Maven image to build the application
FROM openjdk:21 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and src folder into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller base image for the final application
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage to the final image
COPY --from=build /app/target/animaldemo-0.0.1-SNAPSHOT.jar animaldemo.jar

# Specify the command to run the jar
ENTRYPOINT ["java", "-jar", "animaldemo.jar"]

# Expose the port the application runs on
EXPOSE 8080


