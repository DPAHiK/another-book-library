FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY /target/*.jar /app/*.jar
EXPOSE ${TRACKER_PORT}
ENTRYPOINT ["java", "-jar", "/app/*.jar"]