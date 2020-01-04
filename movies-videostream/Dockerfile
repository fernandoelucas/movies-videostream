FROM maven:3.5-jdk-8-alpine as build
COPY ./ ./
WORKDIR /movies-videostream
RUN mvn install

FROM openjdk:8u131-jdk-alpine
ADD target/movies-videostream-0.0.1-SNAPSHOT.jar app-movies-videostream.jar
EXPOSE 8010
ENTRYPOINT ["java","-jar","/app-movies-videostream.jar"] 