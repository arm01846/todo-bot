FROM gradle:latest AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ["gradle", "bootJar"]

FROM openjdk:8-alpine
COPY --from=builder /home/gradle/src/build/libs/todo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-server", "$JAVA_OPTS"]
CMD ["-jar", "app.jar"]
