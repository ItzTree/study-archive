# Chap08

책의 Day 4 CLASS 04 ~ Day 4 CLASS 07 파트를 다룬다.  

### 검색 기능 추가 구현
검색과 관련된 소스는 getBoardList.jsp 파일에서 확인할 수 있다. 검색 화면에서 검색 조건과 검색 키워드에 해당하는 파라미터 이름은 `searchCondition`과 `searchKeyword`이다. 사용자가 입력한 파라미터 값들이 BoardVO라는 Command 객체에 자동으로 채워지므로, 이에 대한 Getter/Setter 메소드도 필요하다. 검색 조건과 검색 키워드가 전달되지 않을 때, 두 변수에는 null이 설정되는데 기본값을 적절히 설정하여 비즈니스 컴포넌트에 전달하기 위한 null 체크 로직도 필요하다.  
BoardDAO 클래스에서는 SQL 명령어와 `getBoardList()` 메소드를 수정한다. 검색 조건(searchCondition) 값이 TITLE인지 CONTENT인지에 따라 쿼리가 다르게 동작하고, 메소드에는 분기 처리 로직을 추가해야 한다.  

### 파일 업로드
글 등록 화면에서 파일을 업로드할 수 있게 만들기 위하여 \<form> 태그에 `enctype` 속성을 추가하고, 속성값을 `multipart/form-data`로 지정한다.  
사용자가 입력한 데이터에 제목, 작성자, 내용뿐만 아니라 업로드할 파일 정보가 추가되었으므로, Command 객체로 사용하는 BoardVO에 업로드와 관련한 변수를 추가하고 Getter/Setter 메소드도 추가한다.  
파일 업로드 관련 라이브러리를 추가하고, 스프링 설정 파일에 \<bean> 등록한다. 스프링 설정 파일에 등록되는 클래스 중 이름이 "Resolver"로 끝나는 클래스는 대부분 `id`가 정해져 있으니 신경을 써야 한다.  
스프링 컨테이너가 BoardVO 객체를 생성하고, 클라이언트로부터 파라미터 정보(제목, 파일 등)를 추출하여 BoardVO 객체의 Setter 메소드를 통해 입력 값들을 설정한다. 값이 설정된 BoardVO 객체를 `insertBoard()` 메소드를 호출할 때 인자로 전달한다.  

### 예외 처리



### 커밋
- [15e888b](https://github.com/ItzTree/study-archive/commit/15e888b2cfbf78d9986e6a68f05f005d1270e8e1)  
    검색 기능을 추가로 구현한다. '제목'과 '내용'에 따른 검색 결과를 다르게 나타낸다.  
- [933a352](https://github.com/ItzTree/study-archive/commit/933a352e249455bb1b8f21804e25da2faac80118)  
    파일 업로드 기능을 추가한다.  
- 