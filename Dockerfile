# Step 1: Build the Maven project using Java 17
FROM maven:3-eclipse-temurin-17-alpine as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -B -U

# Step 2: Final image
FROM docker.io/openjdk:17-oracle
LABEL maintainer="Cristian Ricardo Ortega Ramírez <cristian.ortega@comunidad.unam.mx>"

WORKDIR /app

ARG MONGO_URI=mongodb://usuario:contraseña@host:puerto/nombre_base_datos
ARG MINIO_URL=http://localhost:9000
ARG MINIO_USER=minio_user
ARG MINIO_PASS=minio_pass
ARG MINIO_BUCKET_NAME=bucket_name
ARG APP_PORT=8080

ENV MONGO_URI=${MONGO_URI}
ENV MINIO_URL=${MINIO_URL} 
ENV MINIO_USER=${MINIO_USER} 
ENV MINIO_PASSWORD=${MINIO_PASS} 
ENV MINIO_BUCKET_NAME=${MINIO_BUCKET_NAME}
ENV APP_PORT=${APP_PORT}

#COPY target/register-image-service-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder /app/target/*.jar /app/app.jar

#RUN microdnf install -y curl && microdnf clean all
EXPOSE ${APP_PORT}

CMD ["java", "-jar", "app.jar"]
