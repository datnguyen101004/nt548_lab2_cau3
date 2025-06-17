# Build stage: dùng Maven + Java 21 để build ứng dụng
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Thư mục làm việc trong container
WORKDIR /app

# Copy file cấu hình Maven
COPY pom.xml .

# Tải dependencies để cache hiệu quả
RUN mvn dependency:go-offline

# Copy mã nguồn
COPY src ./src

# Build ứng dụng, có thể skip tests nếu cần
RUN mvn clean package -DskipTests

# Run stage: Java 21 runtime nhẹ
FROM eclipse-temurin:21-jdk-jammy

# Thư mục chứa app
WORKDIR /app

# Copy file JAR đã build từ giai đoạn trước
COPY --from=build /app/target/*.jar app.jar

# Mở cổng API Gateway (ví dụ thường dùng: 8080)
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
