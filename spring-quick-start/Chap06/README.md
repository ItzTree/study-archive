# Chap05

책의 Day 3 CLASS 04 ~ Day 3 CLASS 07 파트를 다룬다.  

### MVC 프레임워크 개발

프레임워크에서 제공하는 Controller를 사용하면 개발과 유지보수이 편의성을 보장해준다. 하지만, 기능과 구조가 복잡하므로 바로 적용하기는 어려우므로 Spring MVC와 동일한 구조의 프레임워크를 직접 구현하여 적용한다.  
이 챕터에서 개발할 MVC 프레임워크의 구조는 다음과 같다.  
| 클래스 | 기능 |
| :--- | :--- |
| DispatcherServlet | 유일한 서블릿 클래스로서 모든 클라이언트의 요청을 가장 먼저 처리하는 Front Controller |
| HandlerMapping | 클라이언트의 요청을 처리할 Controller 매핑 |
| Controller | 실질적인 클라이언트의 요청 처리 |
| ViewResolver | Controller가 리턴한 View 이름으로 실행될 JSP 경로 완성 |

### MVC 프레임워크 구현
DispatcherServlet은 클라이언트의 요청을 가장 먼저 받아들이는 Front Controller로, 요청을 처리하기 위해 직접 무언가를 하지는 않는다. **실질적인 요청 처리**는 각 Controller에서 이루어진다. 구체적인 Controller를 구현하기에 앞서 인터페이스를 구현할 것이다.  
- Controller 인터페이스  
    클라이언트 요청을 받은 DispatcherServlet은 HandlerMapping을 통해 Controller 객체를 검색하고 실행한다. 어떤 Controller 객체가 검색되더라도 같은 코드로 실행하기 위해 Controller 인터페이스를 구현하는 것이다.  
- LoginController 클래스  
    로그인 처리 소스는 DispatcherServlet의 로그인 처리 기능과 같지만, Controller 인터페이스의 `handleRequest()` 메소드를 오버라이딩 했기 대문에 이동할 화면을 리다이렉트하지 않고 리턴한다. 로그인에 실패했을 때 "login"을 리턴하는데, 이는 ViewResolver 클래스에서 따로 처리한다.  
- HandlerMapping 클래스  
    모든 Controller 객체들을 저장하고 있다가 클라이언트의 요청이 들어오면 특정 Controller를 검색하는 기능을 제공한다. DispatcherServlet이 생성되고 `init()` 메소드가 호출될 때 HandlerMapping 객체가 단 한 번 생성된다. 
- ViewResolver 클래스  
    Controller가 리턴한 View 이름에 접두사와 접미사를 결합하여 최종으로 실행될 View 경로와 파일명을 완성한다. ViewResolver도 DispatcherServlet의 `init()` 메소드가 호출될 때 생성된다. 

    




### 커밋
- 