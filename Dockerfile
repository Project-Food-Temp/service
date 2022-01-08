FROM openjdk:8
ADD target/foods-app.jar foods-app.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar","foods-app.jar"]
