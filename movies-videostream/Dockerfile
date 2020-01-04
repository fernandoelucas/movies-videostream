#FROM maven:3.5-jdk-8-alpine as build
#COPY ./ ./
#WORKDIR /movies-videostream
#RUN echo "$PWD"
#RUN mvn install


FROM openjdk:8u131-jdk-alpine
ADD /movies-videostream/target/movies-videostream-0.0.1-SNAPSHOT.jar app-movies-videostream.jar
EXPOSE 8010
ENTRYPOINT ["java","-jar","/app-movies-videostream.jar"] 

# set deployment directory
#WORKDIR /movies-videostream

# copy over the built artifact from the maven image
#COPY --from=maven target/movies-videostream-*.jar ./

# set the startup command to run your binary
#CMD ["java", "-jar", "./target/my-project.jar"]