FROM openjdk:17-jdk-alpine

EXPOSE 8080

ADD /target/spring_boot-task1-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]