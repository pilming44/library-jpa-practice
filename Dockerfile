FROM openjdk:17-jre-slim
COPY build/libs/*SNAPSHOT.jar /home/app.jar
CMD ["java", "-jar", "/home/app.jar"]