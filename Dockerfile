FROM eclipse-temurin:latest
COPY out/artifacts/simpleserver_jar/. /simpleserver_jar
ENTRYPOINT ["java", "-jar", "/simpleserver_jar/simpleserver.jar"]
