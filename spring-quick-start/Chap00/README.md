# Chap00

책의 Day 1 CLASS 01 ~ Day 1 CLASS 02 파트를 다룬다.  
책에서는 Eclipse와 STS를 활용하여 스프링 레거시 프로젝트를 진행하는데 반해, 여기서는 **IntelliJ와 Maven**을 이용하여 프로젝트를 진행한다.  

https://velog.io/@dondonee/Spring-Legacy-Project-%EB%A7%8C%EB%93%A4%EA%B8%B0-1   
위 홈페이지를 참고하여 프로젝트 환경설정을 진행하였다.  

### 커밋
- [951db40](https://github.com/ItzTree/study-archive/commit/951db409a65e787c597f2cf6469eeccb539a22b0)  
  인텔리제이에서 Maven Archetye 프로젝트를 생성하고 pom.xml, servlet-context.xml 등 기본 환경 설정에 필요한 파일들을 작성한다. 여러 디렉토리들을 생성하여 스프링 MVC 구조에 맞게 한다.
- [89cba03](https://github.com/ItzTree/study-archive/commit/89cba035366519a31049cbe5e7025295a6fbbe61)  
  책 예제 실습을 위한 HelloServlet.java을 생성하고, web.xml에 서블릿을 등록한다.`/hello.do` 로 접속하면 다음 메세지가 뜬다.  
  ```
  ===> HelloServlet 객체 생성
  ===> doGet() 메소드 호출
  ```
  `/hello.do` 요청 → web.xml의 `<servlet-mapping>` 확인 → hello라는 servlet-name 발견 → web.xml의 `<servlet>` 확인 → `com.springbook.biz.HelloServlet` 확인 → `doGet()` 메소드 호출
- [4e902e9](https://github.com/ItzTree/study-archive/commit/4e902e97ae4883e807ed299b2bdfcba5bb3cb1f7)  
  다형성(polymorphism)을 이해하기 위한 예제이다.  
  `SamsungTV`와 `LgTV`는 메소드 시그니처가 다르기 때문에 객체를 바꾸면 이에 해당하는 메소드 이름도 모두 바꿔야 한다.  
  하지만, 다형성을 이용하여 TV가 가질 수 있는 메소드 시그니처를 지정하면 객체를 바꾸더라도 코드를 애써 수정하지 않아도 된다.
- [4cf24b5](https://github.com/ItzTree/study-archive/commit/4cf24b5d9ff664b14b8c8cee281211d7ee3b8a55)  
  TV라는 인터페이스를 만들고 SamsungTV와 LgTV가 이를 상속한다. 객체를 변경하더라도 메소드 이름은 바꾸지 않아도 되기에 유지보수가 편해진다.