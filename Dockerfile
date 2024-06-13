FROM ubuntu:20.04

RUN apt-get update && apt-get install -y openjdk-17-jre

COPY build/libs/*SNAPSHOT.jar /home/app.jar

CMD ["java", "-jar", "/home/app.jar"]