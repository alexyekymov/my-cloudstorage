FROM maven:3-eclipse-temurin-17 AS build

WORKDIR /app

# Copy the Maven project descriptor, download dependencies, and cache them
COPY pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-focal

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]