FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
## Configureable
# ARG JAR_FILE
# COPY ${JAR_FILE} app.jar
## Maven
# COPY ./target/blog-service-*.jar app.jar
## Gradle
COPY ./build/libs/blog-service-*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]