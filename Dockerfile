FROM maven:3.8.3-openjdk-17 AS builder
ADD . /app
WORKDIR /app
RUN mvn -f /app/pom.xml clean package

FROM openjdk:17.0.1-jdk-slim
COPY --from=builder /app/target/sample-1.0.0.jar  app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]