FROM openjdk:18-jdk-oracle
EXPOSE 5500
ADD target/cloud-0.0.1-SNAPSHOT.jar run.jar
ENTRYPOINT ["java", "-jar", "/run.jar"]