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
예외가 발생했을 때 사용자에게 적절한 메시지가 담긴 화면을 보여주도록 코딩해야 한다. 이런 예외 처리를 하기 위해 어노테이션 설정 또는 XML 설정을 할 수 있는데, 여기서는 어노테이션을 사용하도록 한다.  
스프링에서는 `@ControllerAdvice`와 `@ExceptionHandler` 어노테이션을 이용하여 예외를 처리할 수 있다. 클래스 위에 @ControllerAdvice("com.springbook.view") 어노테이션에 의해 CommonExceptionHandler 객체가 자동으로 생성되고, "com.springbook.view" 패키지로 시작하는 컨트롤러에서 예외가 발생하는 순간 @ExceptionHandler 어노테이션으로 지정한 예외 처리 메소드가 실행된다.  

### 다국어 처리
언어별로 메시지 파일을 작성하고, 스프링 설정 파일에 이 메시지 파일들을 읽어 들이는 MessageSource 클래스를 bean 등록한다. ResourceBundleMessageSource 클래스도 아이디가 messageSource로 정해져 있다. 그리고, 메시지 파일들이 원래대로라면 `message.messageSource_en.properties` 처럼 정확하게 등록되어 있어야 하지만, 유지보수를 위하여 확장자와 언어 버전을 생략하도록 설정 파일을 작성한다.  
웹 브라우저가 서버에 요청하면 브라우저의 Locale 정보가 HTTP 요청 메시지 헤더에 자동으로 설정되어 전송된다. 스프링은 LocaleResovler를 통해 클라이언트의 Locale 정보를 추출하고 이에 해당하는 언어의 메시지를 적용한다.  
| LocaleResolver 종류 | 기능 설명 |
| :--- | :--- |
| AcceptHeaderLocaleResolver | 브라우저에서 전송된 HTTP 요청 헤더에서 Accept-Language에 설정된 Locale 값 사용 |
| CookieLocaleResolver | Cookie에 저장된 Locale 정보를 추출 |
| SessionLocaleResolver | HttpSession에 저장된 Locale 정보를 추출 |
| FixedLocaleResolver | 웹 요청과 무관하게 특정 Locale로 고정 |
해당 화면의 언어를 변경하기 위해서 LocaleChangeInterceptor 클래스를 인터셉트로 스프링 설정 파일에 등록한다.

### 데이터 변환
지금까지 BOARD 테이블에 저장된 게시글 정보를 저장하기 위해서 BoardVO 객체를 사용했다. BoardVO 객체에 저장된 데이터를 JSON 데이터로 변환하면, 변수와 변수에 저장된 값이 '키:값' 형태로 표현된다.  
일반적으로, 브라우저에서 서블릿이나 JSP 파일을 요청하면 서버는 클라이언트가 요청한 서블릿이나 JSP를 찾아서 **실행**한다. 그리고 실행 결과를 HTTP 응답 프로토콜 메시지 바디에 저장하여 브라우저에 전송하게 되어, 브라우저는 항상 실행 결과 화면만 표시한다. 이 응답 결과를 HTML이 아닌 JSON이나 XML로 변환하여 메시지 바디에 저장하려면 스프링에서 제공하는 Converter를 사용하면 된다.  
글 목록을 검색하여 리턴하는 `dataTransform()` 메소드 위에 @ResponseBody 어노테이션이 추가되었는데, 자바 객체를 HTTP 응답 프로토콜의 몸체로 변환하기 위해 사용한다. 이 메소드의 실행결과는 JSON으로 변환되어 HTTP 응답 바디에 설정된다. 파일 업로드 정보 등 출력 결과에 포함하고 싶지 않은 변수에 대해서는 BoardVO 클래스의 **Getter 메소드**에 @JsonIgnore 어노테이션을 추가하면 된다.  

JSON이 아닌 XML로 정보를 변환하는 방법도 있다. BoardVO 클래스에 @XmlAccessorType을 선언하여 BoardVO 객체를 XML로 변환할 수 있음을 나타내고, `XmlAccessType.FIELD`로 인하여 변수들은 자동으로 자식 엘리먼트로 표현된다. 속성으로 표현하고자 하는 변수에는 @XmlAttribute를, XML 변환에서 제외하고자 하는 변수에는 @XmlTransient를 붙인다.  
XML 문서는 반드시 단 하나의 루트 엘리먼트를 가져야하므로, 여러 게시글 목록을 XML로 표현하기 위해서는 BoardVO 객체 여러 개를 포함하면서 루트 엘리먼트로 사용할 BoardListVO 클래스가 필요하다.  


### 커밋
- [15e888b](https://github.com/ItzTree/study-archive/commit/15e888b2cfbf78d9986e6a68f05f005d1270e8e1)  
    검색 기능을 추가로 구현한다. '제목'과 '내용'에 따른 검색 결과를 다르게 나타낸다.  
- [933a352](https://github.com/ItzTree/study-archive/commit/933a352e249455bb1b8f21804e25da2faac80118)  
    파일 업로드 기능을 추가한다.  
- [aec9fa2](https://github.com/ItzTree/study-archive/commit/aec9fa2139df4242771f5e1d6e616845f352b726)  
    어노테이션을 사용하여 예외 처리 로직을 추가한다.  
- [e31033f](https://github.com/ItzTree/study-archive/commit/e31033fee8a7bac4166bacc87af0da859dfb6b47)  
    다국어 처리 기능을 추가한다.  
- 