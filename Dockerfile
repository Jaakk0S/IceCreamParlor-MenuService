FROM eclipse-temurin:25-jre-alpine-3.22
RUN mvn clean package
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]