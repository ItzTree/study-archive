# Chap06

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
- **Controller 인터페이스  **
    클라이언트 요청을 받은 DispatcherServlet은 HandlerMapping을 통해 Controller 객체를 검색하고 실행한다. 어떤 Controller 객체가 검색되더라도 같은 코드로 실행하기 위해 Controller 인터페이스를 구현하는 것이다.  
- **LoginController 클래스  **
    로그인 처리 소스는 DispatcherServlet의 로그인 처리 기능과 같지만, Controller 인터페이스의 `handleRequest()` 메소드를 오버라이딩 했기 대문에 이동할 화면을 리다이렉트하지 않고 리턴한다. 로그인에 실패했을 때 "login"을 리턴하는데, 이는 ViewResolver 클래스에서 따로 처리한다.  
- **HandlerMapping 클래스  **
    모든 Controller 객체들을 저장하고 있다가 클라이언트의 요청이 들어오면 특정 Controller를 검색하는 기능을 제공한다. DispatcherServlet이 생성되고 `init()` 메소드가 호출될 때 HandlerMapping 객체가 단 한 번 생성된다. 
- **ViewResolver 클래스 ** 
    Controller가 리턴한 View 이름에 접두사와 접미사를 결합하여 최종으로 실행될 View 경로와 파일명을 완성한다. ViewResolver도 DispatcherServlet의 `init()` 메소드가 호출될 때 생성된다. 

클라이언트가 로그인 버튼을 통해 "/login.do" 요청을 전송하면 DispatcherServlet이 요청을 받는다. 서블릿은 HandlerMapping 객체를 통해 로그인 요청을 처리할 LoginController를 검색하고, 이 컨트롤러의 `handleRequest()` 메소드를 호출하면 로그인 로직이 처리된다. 로그인 처리 후 이동할 화면 정보를 리턴받으면 서블릿은 ViewResolver를 통해 JSP 파일의 이름과 경로를 리턴받고, 이를 실행하여 결과가 브라우저에 응답된다.  

### EL/JSTL을 이용한 JSP 화면 처리
JSP 파일에서 Controller 로직에 해당하는 자바 코드를 제거하기 위해 Model 2 아키텍처로 전환하였다. 사용자 입력 정보 추출, DB 연동 처리 등의 Controller 로직에 해당하는 자바 코드는 남아있지 않지만, 여전히 자바 코드는 남아 있는 JSP 파일이 존재한다. 이런 자바 코드도 없애려면 <b>EL(Expression Language)</b>과 <b>JSTL(JSP Standard Tag Library)</b>를 사용하면 된다.  

### Spring MVC
Spring MVC에서 가장 중요한 요소는 **모든 클라이언트의 요청을 가장 먼저 받아들이는 DispatcherServlet**이다. 클라이언트의 요청으로 DispatcherServlet 객체가 생성되고 나면 `init()` 메소드가 자동으로 실행되어 XmlWebApplicationContext라는 스프링 컨테이너가 구동된다.  
DispatcherServlet은 Spring MVC의 유일한 서블릿으로, 서블릿 컨테이너가 web.xml 파일에 등록된 DispatcherServlet만 생성해준다. 하지만 이 서블릿 혼자서는 클라이언트의 요청을 처리할 수 없기 때문에 HandlerMapping, Controller, ViewResolver과 **상호작용**해야 한다. 이러한 객체들을 메모리에 생성하기 위해 서블릿은 스프링 컨테이너를 구동하는 것이다.  
`init()` 메소드는 스프링 설정 파일(action-servlet.xml)을 로딩하여 스프링 컨테이너를 구동시키므로, 이 파일에 HandlerMapping, Controller, ViewResolver 클래스를 bean 등록하면 스프링 컨테이너가 객체들을 생성해준다.  

기존에 사용하던 Controller 클래스를 모두 스프링에서 제공하는 Controller 인터페이스로 구현해야 한다. 이 Controller 인터페이스의 `handleRequest()` 메소드는 ModelAndView라는 객체를 리턴한다. 또한, 스프링 설정 파일에 HandlerMapping과 각 기능에 맞는 Controller를 bean 등록한다.  
이전에는 GetBoardList에서 검색 결과를 세션으로 관리했었다. 하지만, 브라우저 하나당 세션 하나씩 서버 메모리에 생성되고 유지되므로 서버에 부하가 갈 수 있다. 그 대신에 검색 결과를 HttpServletRequest 객체에 저장할 수도 있지만, 여기서는 ModelAndView 객체에 저장하였다. 정확히는 DispatcherServlet이 ModelAndView 객체에서 검색 결과에 해당하는 Model 정보를 HttpServletRequest 객체에 저장하여 JSP로 넘긴다. 


### 커밋
- [99fe82d](https://github.com/ItzTree/study-archive/commit/99fe82d7972f1b7fb25c4d58eafcb931190b3dfd)  
    Controller 인터페이스, HandlerMapping, ViewResolver 등 MVC 프레임워크 기본 토대를 구현한다.  
- [f4c1560](https://github.com/ItzTree/study-archive/commit/f4c156036bb964a8cc844adbf15dfd8b8200f6cf)  
    DispatcherServlet에 있던 컨트롤러 기능을 별도의 컨트롤러 클래스로 분리한다.  
- [c0dfa43](https://github.com/ItzTree/study-archive/commit/c0dfa43bf5e0c50b436953972cb4f3b424cb97dd)  
    EL과 JSTL을 이용하여 화면을 처리한다.  
- [5d1513e](https://github.com/ItzTree/study-archive/commit/5d1513ec263f883c2da24514a2bc391b67bd9c3f)  
    Spring MVC를 적용한다.  