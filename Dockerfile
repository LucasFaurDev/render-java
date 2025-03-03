FROM openjdk:23-jdk-slim
ARG JAR_FILE=target/phonebook-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app-phonebook-server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app-phonebook-server.jar"]