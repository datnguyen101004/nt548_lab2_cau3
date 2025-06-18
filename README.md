# Sử dụng Jenkins để quản lý CI/CD cho ứng dụng microservices

## Mô tả
Dự án này sử dụng **Jenkins** để tự động hóa quy trình CI/CD cho một ứng dụng microservices, triển khai lên **Docker**. Dự án tích hợp:
- **SonarQube**: Kiểm tra chất lượng mã nguồn.
- **Docker**: Container hóa ứng dụng microservices.
- **Jenkins**: Tự động hóa các bước build, test, và deploy.

Mục đích: Xây dựng pipeline CI/CD để tự động triển khai ứng dụng microservices, đảm bảo chất lượng mã nguồn.

**Lưu ý**: Pipeline hiện tại chưa hoàn thiện (theo báo cáo nhóm).

## Yêu cầu tiên quyết
- Cài đặt các công cụ:
    - [Docker](https://www.docker.com/get-started) và [Docker Compose](https://docs.docker.com/compose/install/).
    - [Jenkins](https://www.jenkins.io/download/) (chạy trong container).
    - [SonarQube](https://www.sonarqube.org/downloads/) (chạy trong container).
    - [Maven](https://maven.apache.org/install.html) (để build ứng dụng).
- Tài khoản GitHub để kết nối với Jenkins.

## Hướng dẫn triển khai

### 1. Clone repository
```bash
git clone https://github.com/datnguyen101004/nt548_lab2_cau3.git
cd nt548_lab2_cau3
```

### 2. Chạy Jenkins và SonarQube
Khởi động Jenkins và SonarQube bằng Docker Compose:
```bash
docker compose -f docker-compose-jenkin-sonarqube.yaml up -d
```

Lấy mật khẩu mặc định của Jenkins:
```bash
docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

### 3. Cài đặt Maven trong Jenkins
Truy cập container Jenkins và cài đặt Maven:
```bash
docker exec -it jenkins bash
apt update && apt install -y maven
```

### 4. Cấu hình Jenkins
1. Truy cập Jenkins qua `http://localhost:8080` và đăng nhập bằng mật khẩu mặc định.
2. Cài đặt các plugin cần thiết (ví dụ: Git, Pipeline, Docker, SonarQube Scanner).
3. Tạo job mới:
    - Chọn **Pipeline** và cấu hình để sử dụng `Jenkinsfile` từ repository.
    - Kết nối với repository `datnguyen101004/nt548_lab2_cau3` (nhánh `master`).
4. Cấu hình SonarQube trong Jenkins:
    - Thêm SonarQube server trong **Manage Jenkins > Configure System**.
    - Cập nhật thông tin SonarQube (URL, token) trong pipeline.

### 5. Chạy pipeline
- Push code lên nhánh `main` để kích hoạt pipeline.
- Theo dõi tiến trình trong Jenkins dashboard.
- Kiểm tra báo cáo chất lượng mã nguồn trên SonarQube (`http://localhost:9000`).

**Lưu ý**: Pipeline hiện tại chưa thành công. Cần kiểm tra `Jenkinsfile` và cấu hình SonarQube để khắc phục lỗi.

### 6. Hủy tài nguyên
Dừng và xóa container Docker:
```bash
docker compose -f docker-compose-jenkin-sonarqube.yaml down
```

## Xử lý sự cố
- **Pipeline thất bại**: Kiểm tra log trong Jenkins console output.
- **Lỗi SonarQube**: Đảm bảo SonarQube server đang chạy và token được cấu hình đúng.
- **Lỗi Maven**: Kiểm tra cài đặt Maven trong container Jenkins.
- **Lỗi Docker**: Đảm bảo Docker Compose chạy đúng và các port (8080, 9000) không bị xung đột.