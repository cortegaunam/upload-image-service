FROM openjdk:17-oracle
LABEL maintainer="Cristian Ricardo Ortega Ramírez <cristian.ortega@comunidad.unam.mx>"
ARG MONGO_URI=mongodb://usuario:contraseña@host:puerto/nombre_base_datos
#ARG MONGO_HOST=localhost
#ARG MONGO_PORT=27017
#ARG MONGO_DB=mongo_db
ARG MINIO_URL=http://localhost:9000
ARG MINIO_USER=minio_user
ARG MINIO_PASS=minio_pass
ARG MINIO_BUCKET_NAME=bucket_name
ARG APP_PORT=8080
ENV MONGO_URI=$MONGO_URI
#ENV MONGO_HOST=$MONGO_HOST 
#ENV MONGO_PORT=$MONGO_PORT 
#ENV MONGO_DB=$MONGO_DB
ENV MINIO_URL=$MINIO_URL 
ENV MINIO_USER=$MINIO_USER 
ENV MINIO_PASSWORD=$MINIO_PASS 
ENV MINIO_BUCKET_NAME=$MINIO_BUCKET_NAME
ENV APP_PORT=$APP_PORT
ARG JAR_FILE=target/*.jar
COPY target/*.jar app.jar
EXPOSE $APP_PORT
CMD ["java", "-jar", "/app.jar"]
