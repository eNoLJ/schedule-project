# Schedule Project

본 프로젝트는 Spring Boot와 JPA를 활용하여 구현한 **일정 관리 REST API 프로젝트**이다.  
일정의 생성, 조회, 수정, 삭제 기능을 제공하며, 각 일정에 댓글을 작성할 수 있는 구조로 설계하였다.

단순한 CRUD 구현에 그치지 않고,  
트랜잭션 처리, JPA Auditing, 계층 분리, 비즈니스 로직 검증을 중심으로 설계하였다.

## 프로젝트 구조

프로젝트는 역할에 따라 다음과 같이 계층을 분리하였다.

- **Controller**
    - 클라이언트 요청을 받아 서비스 계층에 위임
    - HTTP 요청/응답 책임만 담당

- **Service**
    - 핵심 비즈니스 로직 처리
    - 트랜잭션 관리
    - 도메인 규칙 검증

- **Entity**
    - 데이터베이스 테이블과 매핑되는 도메인 객체
    - 공통 생성일/수정일 관리

- **Repository**
    - 데이터 접근 계층
    - JPA를 통한 CRUD 처리

- **DTO**
    - 요청/응답 전용 객체
    - 엔티티와 API 스펙 분리

## 주요 기능

### 일정 생성
- 일정 제목, 내용, 작성자명, 비밀번호를 포함하여 일정 생성
- 일정 고유 식별자(ID)는 자동 생성
- 작성일과 수정일은 날짜·시간을 포함하여 저장
- 최초 생성 시 수정일은 작성일과 동일
- 작성일/수정일은 JPA Auditing으로 관리
- API 응답에서 비밀번호는 제외


### 일정 조회

#### 전체 일정 조회
- 작성자명을 기준으로 일정 목록 조회 (선택 조건)
- 작성자명 유무에 따라 하나의 API로 처리
- 수정일 기준 내림차순 정렬
- API 응답에서 비밀번호 제외

#### 선택 일정 조회
- 일정 ID를 이용한 단건 조회
- API 응답에서 비밀번호 제외

### 일정 수정
- 일정 제목, 작성자명만 수정 가능
- 수정 요청 시 비밀번호 검증
- 작성일은 변경되지 않으며, 수정일은 수정 시점으로 갱신
- API 응답에서 비밀번호 제외

### 일정 삭제
- 일정 ID와 비밀번호를 통해 일정 삭제
- 비밀번호 검증 후 삭제 처리

### 댓글 생성
- 일정에 댓글 작성 가능
- 댓글 내용, 작성자명, 비밀번호, 작성일/수정일, 일정 ID 저장
- 댓글 고유 식별자(ID)는 자동 생성
- 작성일/수정일은 JPA Auditing으로 관리
- 하나의 일정에는 댓글 최대 10개까지 작성 가능
- API 응답에서 비밀번호 제외

### 일정 단건 조회 업그레이드
- 일정 단건 조회 시 해당 일정의 댓글 목록 함께 응답
- API 응답에서 비밀번호 제외

## RED
<details>
    <summary>RED</summary>
<img width="569" height="490" alt="Image" src="https://github.com/user-attachments/assets/130e67bc-2d80-430a-93f6-710efb03cbef" />
</details>

## API 명세서

<details>
    <summary>API 명세서</summary>

### 1. 일정 생성

POST `http://localhost:8080/schedules`

#### Request body
````
{
"title": "일정",
"description": "내용",
"author": "eno",
"password": "1234"
}
````
#### Response header
````
status code 201 CREATED
````

#### Response body
````
{
    "author": "eno",
    "createdAt": "2025-12-31T20:32:25.069979",
    "description": "내용",
    "id": 1,
    "modifiedAt": "2025-12-31T20:32:25.069979",
    "title": "일정"
}
````

### 2. 전체 일정 조회

GET `http://localhost:8080/schedules`

#### Query params
author | 작성자

#### Response header
````
status code 200 OK
````

#### Response body
````
[
    {
        "author": "eno",
        "createdAt": "2026-01-02T10:35:33.530889",
        "description": "내용2",
        "id": 2,
        "modifiedAt": "2026-01-02T10:35:33.530889",
        "title": "일정2"
    },
    {
        "author": "eno",
        "createdAt": "2026-01-02T10:34:49.254066",
        "description": "내용",
        "id": 1,
        "modifiedAt": "2026-01-02T10:34:49.254066",
        "title": "일정"
    }
]
````

### 3. 선택 일정 조회

GET `http://localhost:8080/schedules/1`

#### Path variable

scheduleId | 스케줄 id

#### Response header
````
status code 200 OK
````

#### Response body
````
{
    "author": "eno",
    "comments": [
        {
            "author": "eno",
            "createdAt": "2026-01-02T10:34:57.179012",
            "description": "코멘트",
            "id": 1,
            "modifiedAt": "2026-01-02T10:34:57.179012",
            "scheduleId": 1
        }
    ],
    "createdAt": "2026-01-02T10:34:49.254066",
    "description": "내용",
    "id": 1,
    "modifiedAt": "2026-01-02T10:34:49.254066",
    "title": "일정"
}
````

### 4. 일정 수정

PATCH `http://localhost:8080/schedules/1`

#### Path variable

scheduleId | 스케줄 id

#### Request body
````
{
"title": "일정 수정",
"author": "noe",
"password": "1234"
}
````

#### Response header
````
status code 200 OK
````

#### Response body
````
{
    "author": "noe",
    "createdAt": "2026-01-02T10:34:49.254066",
    "description": "내용",
    "id": 1,
    "modifiedAt": "2026-01-02T10:36:34.120073",
    "title": "일정 수정"
}
````

### 5. 일정 삭제

DELETE `http://localhost:8080/schedules/1`

#### Path variable

scheduleId | 스케줄 id

#### Request body
````
{
"password": "1234"
}
````

#### Response header
````
status code 204 NO CONTENT
````

### 6. 댓글 생성

POST `http://localhost:8080/schedules/1/comments`

#### Path variable

scheduleId | 스케줄 id

#### Request body
````
{
    "description": "코멘트",
    "author": "eno",
    "password": "1234"
}
````

#### Response header
````
status code 201 CREATED
````

#### Response body
````
{
    "author": "eno",
    "createdAt": "2026-01-02T10:34:57.179012",
    "description": "코멘트",
    "id": 1,
    "modifiedAt": "2026-01-02T10:34:57.179012",
    "scheduleId": 1
}
````
</details>
