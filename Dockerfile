# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and the pom.xml file
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Ensure the Maven wrapper has execute permissions
RUN chmod +x mvnw

# Copy the source code
COPY src src

# Run the Maven build command to generate the jar file
RUN ./mvnw clean package -DskipTests

# Verify the target directory contents and set permissions
RUN ls -al target

# Copy the generated jar file to the container
COPY target/AuthenticationServer-0.0.1.jar app.jar

# Expose the port your application will run on
EXPOSE 8083

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
