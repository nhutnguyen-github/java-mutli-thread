FROM openjdk:17
WORKDIR /app_java/multithread
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]