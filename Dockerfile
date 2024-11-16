FROM openjdk:17-oracle
LABEL maintainer="Cristian Ricardo Ortega Ramírez <cristian.ortega@comunidad.unam.mx>"
EXPOSE 8081
ARG mongo_host=localhost
ARG mongo_port=27017
ARG mongo_db=mongo_db
ARG minio_url=http://localhost:9000
ARG minio_user=minio_user
ARG minio_pass=minio_pass
ARG minio_bucket_name=bucket_name
ENV MONGO_HOST=$mongo_host 
ENV MONGO_PORT=$mongo_port 
ENV MONGO_DB=$mongo_db 
ENV MINIO_URL=$minio_url 
ENV MINIO_USER=$minio_user 
ENV MINIO_PASSWORD=$minio_pass 
ENV MINIO_BUCKET_NAME=$minio_bucket_name
ARG JAR_FILE=target/*.jar
COPY target/*.jar app.jar
CMD ["java", "-jar", "/app.jar"]
