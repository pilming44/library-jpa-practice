FROM openjdk:17-slim
COPY build/libs/*SNAPSHOT.jar /home/app.jar
CMD ["java", "-jar", "/home/app.jar"]