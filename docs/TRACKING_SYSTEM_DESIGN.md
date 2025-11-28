# 사용자 행동 추적 및 히트맵 시스템 설계서 (User Behavior Tracking System Design)

## 1. 개요 (Overview)
본 문서는 사용자의 웹사이트 상호작용(클릭, 스크롤, 마우스 이동 등)을 추적하고, 이를 시각화(히트맵, 통계)하여 사용자 경험(UX)을 분석하기 위한 시스템 설계서입니다.

## 2. 시스템 아키텍처 (System Architecture)

### 2.1 기술 스택
- **Backend**: Kotlin, Spring Boot 3.x
- **Database**: MongoDB (NoSQL for high-volume event data)
- **Frontend**: Vanilla JS (Tracking Script), React/Vue/Thymeleaf (Dashboard)
- **Image Processing**: html2canvas (Client-side capture)

### 2.2 데이터 흐름
1. **User Visit**: 사용자가 웹사이트에 접속.
2. **Tracking Script Load**: `tracker.js`가 로드됨.
3. **Event Capture**: 클릭, 이동, 스크롤 이벤트 리스너 등록.
4. **Data Buffering**: 이벤트 데이터를 메모리에 버퍼링 (네트워크 부하 감소).
5. **Periodic Flush**: 일정 주기(예: 5초) 또는 페이지 이탈 시 데이터를 서버로 전송 (`POST /api/track/events`).
6. **Snapshot Check**: 페이지 로드 시, 해당 페이지의 스크린샷 존재 여부 확인. 없으면 `html2canvas`로 캡처 후 전송.
7. **Data Aggregation**: 서버는 데이터를 MongoDB에 저장하고, 분석 요청 시 집계 수행.
8. **Visualization**: 관리자 대시보드에서 스크린샷 위에 좌표 데이터를 매핑하여 히트맵 렌더링.

---

## 3. 데이터베이스 설계 (MongoDB Schema)

### 3.1 UserSession (Collection)
사용자가 방문 세션을 정의합니다.

```json
{
  "_id": "ObjectId",
  "sessionId": "UUID (Client Generated)",
  "userId": "String (Optional)",
  "ipAddress": "String",
  "userAgent": "String",
  "deviceType": "String (MOBILE | DESKTOP | TABLET)",
  "screenResolution": "1920x1080",
  "referrer": "String",
  "entryPage": "String",
  "createdAt": "ISODate",
  "lastActiveAt": "ISODate"
}
```

### 3.2 TrackingEvent (Collection)
개별 행동 이벤트를 저장합니다.

```json
{
  "_id": "ObjectId",
  "sessionId": "String (Ref: UserSession.sessionId)",
  "pageUrl": "String",
  "eventType": "String (CLICK | MOVE | SCROLL | RESIZE)",
  "x": "Integer",
  "y": "Integer",
  "targetElement": "String (CSS Selector)",
  "timestamp": "ISODate",
  "metadata": {
    "scrollDepth": "Integer (For SCROLL)",
    "duration": "Integer (For HOVER)"
  }
}
```

### 3.3 PageSnapshot (Collection)
히트맵 배경으로 사용할 페이지 스크린샷을 관리합니다.

```json
{
  "_id": "ObjectId",
  "pageUrl": "String (Index)",
  "deviceType": "String (DESKTOP | MOBILE)",
  "resolutionWidth": "Integer",
  "imagePath": "String (File System Path or S3 URL)",
  "createdAt": "ISODate",
  "updatedAt": "ISODate"
}
```

---

## 4. API 설계 (API Endpoints)

### 4.1 Tracking API
- `POST /api/track/session`: 세션 시작 (구현 완료)
- `POST /api/track/events`: 이벤트 배치 전송 (구현 완료)

### 4.2 Snapshot API
- `GET /api/track/snapshot/check`: 스크린샷 필요 여부 확인
  - Query: `url`, `width`
  - Response: `{ required: boolean }`
- `POST /api/track/snapshot`: 스크린샷 이미지 업로드
  - Body: `MultipartFile` (Image), `url`, `deviceType`

### 4.3 Analytics API
- `GET /api/analytics/heatmap`: 히트맵 데이터 조회
  - Query: `url`, `startDate`, `endDate`
  - Response: `{ snapshotUrl: "...", points: [{x, y, value}, ...] }`
- `GET /api/analytics/stats`: 기본 통계 (PV, UV, 체류시간 등)

---

## 5. 스크린샷 시스템 구현 전략 (Screenshot Implementation)

### 5.1 클라이언트 사이드 캡처 (Client-side Capture)
서버 부하를 줄이고, 사용자가 실제로 보는 화면을 정확히 담기 위해 클라이언트에서 캡처를 수행합니다.

1. **라이브러리**: `html2canvas` 사용.
2. **트리거 조건**:
   - 사용자가 페이지에 접속.
   - 트래킹 스크립트가 서버에 `GET /api/track/snapshot/check` 요청.
   - 서버는 해당 URL과 해상도 범위에 맞는 최신 스크린샷이 있는지 확인.
   - 없으면 `required: true` 응답.
3. **캡처 및 전송**:
   - `required: true`인 경우, `html2canvas(document.body)` 실행.
   - 생성된 Canvas를 Blob으로 변환.
   - `FormData`에 담아 `POST /api/track/snapshot`으로 전송.

---

## 6. 히트맵 시각화 (Heatmap Visualization)

1. **Canvas Layering**:
   - Layer 1 (Bottom): 서버에서 받아온 스크린샷 이미지.
   - Layer 2 (Top): `heatmap.js` 또는 `simpleheat` 라이브러리를 사용한 히트맵 오버레이.
2. **좌표 보정**:
   - 저장 시 `relativeX = x / windowWidth` 형태로 저장하고, 렌더링 시 `drawX = relativeX * snapshotWidth`로 계산하는 방식 권장.

---

## 7. 구현 체크리스트 (Implementation Checklist)

### Phase 1: 데이터 수집 (Data Collection)
- [x] **Task 1.1: MongoDB 엔티티 및 리포지토리 구현**
    - `UserSession`, `TrackingEvent`, `PageSnapshot` Entity & Repository 생성 완료.
- [x] **Task 1.2: 수집용 API 개발**
    - `TrackingController` 구현 완료 (`/session`, `/events`).
- [x] **Task 1.3: 프론트엔드 트래커 스크립트 (Tracker.js)**
    - `tracker.js` 파일 생성.
    - 마우스 이동, 클릭, 스크롤 이벤트 리스너 구현.
    - 데이터 버퍼링 및 배치 전송 로직 구현.

### Phase 2: 스크린샷 시스템 (Visual Context)
- [ ] **Task 2.1: 스크린샷 API 구현**
    - `POST /api/track/snapshot`: 이미지 업로드 처리 (로컬 저장).
    - `GET /api/track/snapshot/check`: 스크린샷 존재 여부 확인 로직.
- [ ] **Task 2.2: 클라이언트 캡처 로직**
    - `html2canvas` 라이브러리 연동.
    - 페이지 로드 시 체크 후 캡처 및 전송 로직 구현.

### Phase 3: 데이터 시각화 (Dashboard)
- [ ] **Task 3.1: 히트맵 데이터 집계 API**
    - `AnalyticsController` 생성.
    - `GET /api/analytics/heatmap` 구현.
- [ ] **Task 3.2: 대시보드 페이지 구현**
    - 히트맵 뷰어 페이지 (HTML/JS).
    - `heatmap.js` 연동 및 좌표 매핑.
