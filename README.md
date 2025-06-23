


## 📌 프로젝트 개요

```
Notion STT Recorder (MSA 기반 회의록 자동화 시스템)
```

> 음성 회의 내용을 실시간으로 STT 처리하고, 요약 및 키워드 추출을 통해
> Notion에 자동 저장하는 MSA 구조의 백엔드 시스템입니다.

---

## 🔧 기술 스택

| 구성 요소       | 기술                                          |
| ----------- | ------------------------------------------- |
| 언어          | Kotlin                                      |
| 프레임워크       | Spring Boot                                 |
| 아키텍처        | MSA (서비스별 독립 배포)                            |
| 인증/인가       | Spring Security + JWT                       |
| API 게이트웨이   | Spring Cloud Gateway                        |
| 음성 인식       | STT (Google Speech-to-Text / Whisper API 등) |
| 요약 및 자연어 처리 | OpenAI GPT / LangChain (선택)                 |
| 문서 저장       | Notion API                                  |
| 인프라         | Docker / Docker Compose / Kubernetes (선택)   |
| 데이터베이스      | MySQL / Redis (선택)                          |

---

## 🧩 서비스 구성도

```
[ Client ]
   |
   ▼
[ Gateway ]
   ├── /auth           → 인증 서버 (회원가입 / 로그인 / 토큰)
   ├── /speech         → STT 서버 (음성 파일 업로드 및 텍스트 변환)
   ├── /summary        → 요약 서버 (텍스트 요약 및 키워드 추출)
   └── /notion         → Notion 저장 서버 (회의록 템플릿 저장)
```

---

## ✅ 주요 기능

* [x] 회원가입 / 로그인 (JWT 발급)
* [x] STT 처리 (음성파일 → 텍스트 변환)
* [x] 요약 및 키워드 자동 추출
* [x] Notion 회의록 자동 저장
* [ ] 사용자별 회의 관리 UI (예정)

---

## 🛠️ 프로젝트 구조

```
📦 msa-notion-stt/
├── gateway/               # Spring Cloud Gateway
├── auth-service/          # 인증 서버
├── stt-service/           # 음성 텍스트 변환 서버
├── summary-service/       # 요약/키워드 추출 서버
├── notion-service/        # Notion 저장 서버
└── docker-compose.yml     # 전체 시스템 구성
```

---

## 🔐 인증 흐름 (JWT 기반)

1. `/auth/login` → JWT(Access + Refresh) 발급
2. 모든 요청은 Gateway 글로벌 필터에서 토큰 검증
3. 토큰이 유효하면 서비스로 라우팅

---

## 📂 Notion 저장 형식 예시

```markdown
## 회의 요약
- 주제: 프로젝트 일정 정리
- 참석자: A, B, C
- 날짜: 2025.06.22

## 주요 내용 요약
- MVP 출시일: 7월 초
- 백엔드 담당자: A
- 프론트 담당자: B

## 키워드
- MSA, JWT, Notion API, GPT, STT
```

---

## 🚀 실행 방법 (로컬)

```bash
# 공통 설정
$ ./gradlew clean build

# 각 서비스 도커 빌드 및 실행
$ docker-compose up --build
```

---

## 📌 협업 & 확장 계획

* Slack Webhook 연동 (회의 알림 전송)
* Admin 페이지 개발
* S3에 음성파일 저장 기능 추가

---

## 👤 개발자

| 이름  | 역할                     | 기술 스택                                        |
| --- | ---------------------- | -------------------------------------------- |
| 리나스 | 백엔드 전반 / 인프라 / 아키텍처 설계 | Kotlin, Spring Boot, JWT, Docker, Notion API |

---

