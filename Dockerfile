FROM eclipse-temurin:17-jre
COPY target/*.jar app.jar
COPY target/classes/cdt.txt /temp/cdt.txt
COPY target/classes/mv.txt /temp/mv.txt

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
