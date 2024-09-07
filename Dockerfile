FROM openjdk:17-alpine
LABEL maintainer="developer.baz@gmail.com"
WORKDIR /usr/local/bin/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} account-statements.jar
EXPOSE 9696
ENTRYPOINT ["java","-jar","account-statements.jar"]