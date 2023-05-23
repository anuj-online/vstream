FROM maven:3.8.3-openjdk-17 AS build
COPY / /a
RUN mvn -f /a/pom.xml clean package -Pproduction

FROM eclipse-temurin:17-jre
COPY --from=build /a/target/*.jar app.jar
COPY --from=build /a/gdrivemoviedb.json /file/movie.json
RUN rm -fr /a
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]