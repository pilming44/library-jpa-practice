# API 요구사항
- - -
### 저자등록
- 저자 이름으로 저자를 등록한다.
### 출판사 등록
- 출판사 이름으로 출판사를 등록한다.
### 책 등록
- 책 제목, 저자, 출판사, 보유상태, 보유수량으로 책을 등록한다.
### 책 목록 조회
- 책 정보(제목, 저자, 출판사, 총 보유수량, 대여중인 수량)를 목록으로 조회.
- 페이징 제공
### 책 상세 조회
- 책 정보(제목, 저자, 출판사, 총 보유수량, 대여중인 수량, `보유 책 정보`)을 리턴
- `보유책 정보`(대여자, 대여 일시, 반납 예정일, 연체일 수)는 목록으로 리턴 (페이징 미제공)
### 책 대여
- 책을 대여자이름, 대여일시로 대여한다.
- 더이상 대여할수있는 책이 없다면 책의 대여 가능여부를 `대여 불가능`으로 변경한다.
- 총 보유 권수에 따른 대여기간을 적용한다.
  - 1~2권 : 7일
  - 3~5권 : 10일
  - 6권이상 : 14일
### 책 반납
- 대여자 이름, 책 제목으로 책을 반납한다.
- 응답으로 연체비 정보 리턴
  - 미연체 : 0원
  - 연체 1일 ~ 2일 : 일 당 100원
  - 연체 3일 ~ 5일 : 일 당 200원
  - 연체 6일 ~ 10일 : 일 당 300원
  - 연체 10일 초과 : 일 당 500원

# API 기능목록
- - -
### 저자 등록
- [x] 저자 이름 입력
  - [x] 저자 이름이 없다면 예외발생 - MethodArgumentNotValidException
  - [x] 동일한 이름의 저자가 이미 존재하는 경우 예외 발생 - DuplicateException
### 출판사 등록
- [x] 출판사 명 입력
  - [x] 출판사 이름이 없다면 예외발생 - MethodArgumentNotValidException
  - [x] 동일한 이름의 출판사가 이미 존재하는 경우 예외 발생 - DuplicateException
### 책 등록
- [x] 제목 입력
  - [x] 제목이 없으면 예외발생 - MethodArgumentNotValidException
- [x] 저자 입력
  - [x] 저자가 없으면 예외발생 - MethodArgumentNotValidException
  - [x] 존재하지않는 저자일경우 예외발생 - EntityNotFoundException
- [x] 출판사 입력
  - [x] 출판사가 없으면 예외발생 - MethodArgumentNotValidException
  - [x] 존재하지않는 출판사일경우 예외발생 - EntityNotFoundException
- [x] 보유 상태 입력 (보유중, 미보유)
  - [x] 보유상태값이 없다면 예외발생 - MethodArgumentNotValidException
  - [x] 존재하지않는 상태일경우 예외발생 - IllegalArgumentException
- [x] 보유 수량 입력
  - [x] 보유수량 값이 없거나 0보다 작다면 예외 발생 - MethodArgumentNotValidException
  - [x] 상태가 보유중인데 수량이 0보다 작거나 같을경우 예외발생 - IllegalArgumentException
  - [x] 상태가 미보유인데 수량이 0보다 크다면 예외발생 - IllegalArgumentException
- [x] 동일한 제목, 저자, 출판사의 책이 이미 존재하는 경우 예외 발생 - DuplicateException
### 책 목록 조회
- [x] 책 정보 리스트 조회
  - 제목
  - 저자
  - 출판사
  - 총 보유 수량
  - 대여중인 수량
- [x] 책 목록 조회 시 제목, 저자, 출판사로 검색 기능 제공
- [x] 페이징 제공
### 책 상세조회
- [x] 존재하지 않는 책을 조회할경우 예외 발생 - EntityNotFoundException
- [x] 책 정보 조회
  - 제목
  - 저자
  - 출판사
  - 총 보유수량
  - 대여 가능 여부(`대여가능`, `대여불가능`)
- [x] 보유 책 정보
  - 대여자 명
  - 대여 일시
  - 반납 예정일
  - 연체일 수
### 책 대여
- [x] 존재하지않는 책을 대여할경우 예외 발생 - EntityNotFoundException
- [x] 책 id 입력
  - [x] 책 id가 없다면 예외 발생 - MethodArgumentNotValidException
- [x] 대여자 이름 입력
  - [x] 대여자 이름이 없으면 예외 발생 - MethodArgumentNotValidException
- [x] 대여 일시 입력
  - [x] 대여 일시가 없으면 예외발생 - MethodArgumentNotValidException
- [x] 더이상 대여할수있는 책이 없다면 책의 대여 가능여부를 `대여 불가능`으로 변경
- [x] 총 보유 권수에 따른 대여기간 적용
  - 1~2권 : 7일
  - 3~5권 : 10일
  - 6권이상 : 14일
- [x] 이미 대여 중인 책을 대여하려는 경우 예외 발생 - BorrowException

### 책 반납, 연체비 조회
- [ ] 대여자 이름, 책 제목 입력
  - [ ] 대여자 이름 또는 책 제목이 없다면 예외 발생 - MethodArgumentNotValidException
  - [ ] 해당 대여 정보가 존재하지 않는 경우 예외 발생 - EntityNotFoundException
- [ ] 반납 일시 입력
  - [ ] 반납 일시가 없다면 예외 발생 - MethodArgumentNotValidException
  - [ ] 대여일시보다 빠르면 예외발생 - ReturnException
- [ ] 대여 불가능 상태였다면 반납 후 `대여 가능`으로 변경
- [ ] 반납 일시와 대여 일시를 기준으로 연체 일 수를 계산하여 연체비정보 리턴
  - 연체 1일 ~ 2일 : 일 당 100원
  - 연체 3일 ~ 5일 : 일 당 200원
  - 연체 6일 ~ 10일 : 일 당 300원
  - 연체 10일 초과 : 일 당 500원