ARG BASE_IMAGE=maven:3.8.7-amazoncorretto-17
FROM $BASE_IMAGE AS stage1
WORKDIR /app
ADD pom.xml /app
RUN mvn dependency:go-offline -B
COPY ./ /app/
RUN mvn clean package -DskipTests


FROM amazoncorretto:17-alpine-jdk as stage2
WORKDIR /app
RUN apk update && apk add curl
COPY --from=stage1 /app/target/foo-service-0.0.1-SNAPSHOT.jar /app
USER root
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "foo-service-0.0.1-SNAPSHOT.jar"]
CMD ["--server.port=8080"]
