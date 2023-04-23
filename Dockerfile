FROM openjdk:17

COPY target/netty-test-*-with-dependencies.jar /app/app.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "app.jar"]