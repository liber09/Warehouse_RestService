FROM bitnami/wildfly:29.0.1
LABEL authors="lindabergsangel"
EXPOSE 8080
COPY target/Warehouse_RestService-1.0-SNAPSHOT /app
ENV WILDFLY_USERNAME=user, WILDFLY_PASSWORD=password