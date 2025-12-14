FROM ubuntu:24.04 AS base

RUN apt-get update
RUN apt-get install -y openjdk-25-jdk maven curl




FROM base AS build

COPY . .
RUN mvn clean package





FROM eclipse-temurin:25-jre-alpine-3.22 AS production

COPY --from=build target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
