FROM jelastic/maven:3.9.5-openjdk-21 AS build
LABEL team="Group4"

COPY src /home/app/src
COPY pom.xml /home/app
COPY .mvn /home/app/.mvn
COPY mvnw /home/app/mvnw
COPY mvnw.cmd /home/app/mnvw.cmd

RUN mvn -f /home/app/pom.xml clean package
EXPOSE 8080

ENTRYPOINT ["java","-jar","/home/app/target/review-management-service.jar"]

# java,-jar,/home/app/target/review-management-service.jar