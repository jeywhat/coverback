FROM adoptopenjdk/openjdk11:alpine-slim

VOLUME /games

COPY target/*.jar app.jar

EXPOSE 8080 8080

ENTRYPOINT ["java","-jar","/app.jar"]


