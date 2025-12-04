FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
COPY tls-db ./tls-db
COPY tls-domain ./tls-domain
COPY tls-impl ./tls-impl
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine
LABEL authors="AlekseiDrozdov"
VOLUME /tmp
COPY --from=build /app/tls-impl/target/tls-impl-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]