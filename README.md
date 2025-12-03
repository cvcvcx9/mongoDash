# MongoDash

## 프로젝트 개요

MongoDash는 Kotlin으로 구축된 Spring Boot 애플리케이션으로, MongoDB에 저장된 이벤트 데이터를 관리하고 상호 작용하도록 설계되었습니다. 이는 이벤트 관련 작업을 처리하기 위한 백엔드를 제공하며, 대시보드 또는 이벤트 관리 시스템의 기반이 될 수 있습니다.

## 주요 기능

*   **이벤트 관리**: 이벤트 데이터에 대한 CRUD(생성, 읽기, 업데이트, 삭제) 작업.
*   **RESTful API**: 이벤트 리소스와 상호 작용하기 위한 엔드포인트 제공.
*   **MongoDB 통합**: Spring Data MongoDB를 사용한 원활한 데이터 영속성.

## 사용 기술

*   **Kotlin**: 주요 프로그래밍 언어.
*   **Spring Boot**: 견고하고 프로덕션 준비가 된 애플리케이션 구축을 위한 프레임워크.
*   **Spring Data MongoDB**: MongoDB 데이터 액세스를 단순화.
*   **Gradle**: 빌드 자동화 도구.

## 시작하기

### 전제 조건

*   Java Development Kit (JDK) 21 이상
*   MongoDB 인스턴스 (로컬 또는 원격)

### 애플리케이션 실행

1.  **저장소 복제:**
    ```bash
    git clone <repository-url>
    cd MongoDash
    ```
2.  **MongoDB 설정:**
    `src/main/resources/application.properties` 파일을 MongoDB 연결 정보로 업데이트하세요:
    ```properties
    spring.mongodb.uri=mongodb://localhost:27017/mongodashdb
    ```
    (필요에 따라 `localhost:27017` 및 `mongodashdb`를 변경하세요)

3.  **빌드 및 실행:**
    ```bash
    ./gradlew bootRun
    ```
    애플리케이션은 기본적으로 `http://localhost:8080`에서 시작됩니다.

## API 엔드포인트

(`EventController.kt`를 기반으로 한 기본적인 이벤트 CRUD 가정)

*   `POST /api/events`: 새 이벤트 생성.
*   `GET /api/events`: 모든 이벤트 조회.
*   `GET /api/events/{userId}`: ID로 이벤트 조회.
*   `DELETE /api/events/{userId}`: ID로 이벤트 삭제.

---
