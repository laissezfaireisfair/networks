FROM openjdk:21

VOLUME /workspace

COPY build/libs/api-0.0.1-SNAPSHOT.jar /workspace/app.jar

CMD ["sh", "-c", "java -jar /workspace/app.jar"]
