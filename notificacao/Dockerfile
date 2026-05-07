FROM gradle:8.14-jdk21 AS BUILD

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=BUILD /app/build/libs/*.jar /app/notificacao.jar

EXPOSE 8083

CMD ["java", "-jar", "/app/notificacao.jar"]

