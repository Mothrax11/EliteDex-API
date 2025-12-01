FROM eclipse-temurin:21-jdk
LABEL authors="Raúl López González"
COPY ./target/api-0.0.1-SNAPSHOT.jar ./app/app.jar

ENTRYPOINT ["java", "-jar", "./app/app.jar"]