FROM node as frontend
WORKDIR /frontend
COPY frontend .
RUN npm ci
RUN npm run-script build

FROM maven:3.6.3-jdk-8 as backend
WORKDIR /backend
COPY . .
RUN mkdir -p src/main/resources/static
COPY --from=frontend /frontend/build src/main/resources/static
RUN mvn clean verify

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY --from=backend /backend/${JAR_FILE} ./app.jar
EXPOSE 8080
RUN adduser -D user
USER user
CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar app.jar" ]
