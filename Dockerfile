FROM openjdk:17-alpine as build

RUN apk add --no-cache bash maven

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install

RUN ls -la

FROM openjdk:17-alpine

COPY target/class-checker-sed-1.0.0-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]