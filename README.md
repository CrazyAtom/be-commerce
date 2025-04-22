# 상품 가격 통계 서비스

## 프로젝트 개요
상품 가격을 관리하고 통계를 제공하는 Spring Boot 기반 웹 서비스입니다.

## 기술 스택
- Backend: Java 17, Spring Boot
- Frontend: HTML, JavaScript
- Database: H2
- Build: Gradle
- Architecture: Multi-module project

## 주요 기능
1. 카테고리별 가격 통계
   - 전체 카테고리 최저가 조회
   - 특정 카테고리 최저가/최고가 조회

2. 브랜드 관리
   - 브랜드별 최저가 조회
   - 브랜드 등록 (상품 포함)
   - 브랜드 삭제

3. 상품 관리
   - 상품 가격 수정

## 프로젝트 구조
```
project/
├── boot/                  # 애플리케이션 실행 모듈
├── domain/               # 도메인 로직 모듈
│   ├── controller/      # API 컨트롤러
│   ├── service/         # 비즈니스 로직
│   ├── repository/      # 데이터 접근 계층
│   ├── entity/         # 도메인 엔티티
│   ├── dto/            # 데이터 전송 객체
│   └── enums/          # 열거형 상수
└── web/                 # 웹 리소스 모듈
    └── resources/
        └── static/     # 정적 리소스 (JS, HTML)
```

## API 엔드포인트
- `/api/stats/category/all` : 전체 카테고리 최저가 조회
- `/api/stats/category` : 특정 카테고리 가격 통계
- `/api/stats/brand` : 브랜드 최저가 조회
- `/api/product` : 상품 관리 (등록, 수정)
- `/api/product?name={brandName}` : 브랜드 삭제

## 데이터 초기화
초기 데이터는 `import.sql`을 통해 로드됩니다.

## 참고사항
- 카테고리: TOPS, OUTERWEAR, PANTS, SNEAKERS, BAG, HAT, SOCKS, ACCESSORIES
- 브랜드별로 모든 카테고리의 상품을 보유해야 합니다.
- 가격 통계는 사용 가능한(`use=true`) 상품만 대상으로 합니다.
