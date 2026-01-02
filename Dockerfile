FROM eclipse-temurin:17-jre
LABEL authors="hcy18"

# 设置工作目录
WORKDIR /app

COPY target/shopmind-auth-service-1.0-SNAPSHOT.jar shopmind-auth-service.jar

EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "/app/shopmind-auth-service.jar"]

