FROM adoptopenjdk/openjdk11:alpine
EXPOSE 9999
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh", "bill-service:8082", "--timeout=110", "--", "java", "-jar", "app.jar"]