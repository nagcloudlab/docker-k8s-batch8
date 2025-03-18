ARG BASE_IMAGE=maven:3.8.7-amazoncorretto-17
FROM $BASE_IMAGE AS stage1
WORKDIR /app
ADD pom.xml /app
RUN mvn dependency:go-offline -B
COPY ./ /app/
RUN mvn package -DskipTests


FROM amazoncorretto:17-alpine-jdk as stage2
WORKDIR /app
COPY --from=stage1 /app/target/java-web-service-0.0.1-SNAPSHOT.jar /app
USER nobody
ENV SPRING_PROFILES_ACTIVE=prod
STOPSIGNAL SIGINT
EXPOSE 8080
# HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 CMD curl --fail http://localhost:8080/actuator/health || exit 1
SHELL ["/bin/bash", "-o", "pipefail", "-c"]
# VOLUME /tmp
ENTRYPOINT ["java", "-jar", "java-web-service-0.0.1-SNAPSHOT.jar"]
CMD ["--server.port=8080"]
