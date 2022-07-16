FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8082
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh", "account-service:8081", "--timeout=90", "--", "java", "-jar", "app.jar"]