FROM openjdk:17-slim
COPY build/libs/*SNAPSHOT.jar /home/app.jar
ENTRYPOINT ["java", "-jar", "/home/app.jar"]