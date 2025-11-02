FROM eclipse-temurin:25-jre-alpine-3.22
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]