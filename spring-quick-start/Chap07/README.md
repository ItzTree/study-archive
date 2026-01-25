# Chap07

책의 Day 4 CLASS 01 ~ Day 4 CLASS 03 파트를 다룬다.  

### 어노테이션 기반 MVC
기존에는 스프링 컨테이너가 Controller 클래스를 생성하게 하려면 모두 bean 등록해야 했지만, 어노테이션을 사용하면 클래스 선언부 위에 `@Controller`를 붙이기만 하면 된다. 여기서 어노테이션을 통해 Controller로 인식하게 할 수는 있지만 `/insertBoard.do` 요청에 대해 `insertBoard()` 메소드가 실행되도록 할 수는 없다.  
그래서 HandlerMapping을 대신할 `@RequestMapping`을 사용한다.  
대부분 Controller는 사용자의 입력 정보를 추출하여 VO 객체에 저장한다. 그리고 비즈니스 컴포넌트의 메소드를 호출할 때 VO 객체를 인자로 전달한다. 사용자의 입력 정보가 추가가 될 때마다 기존의 로직에서는 insert, update, delete Controller 클래스를 매번 수정해야 한다. 하지만 **Command 객체**(Controller 메소드 매개변수로 받은 VO 객체를 지칭)를 이용하면 사용자 입력 정보 추출과 VO 객체 생성, 값 설정을 모두 컨테이너가 자동으로 처리한다.  
**서블릿 컨테이너**는 클라이언트의 HTTP 요청이 서버에 전달되는 순간, HttpServletRequest 객체를 생성하고 HTTP 프로토콜에 설정된 모든 정보를 추출하여 HttpServletRequest 객체에 저장한다. DispatcherServlet이 `service()` 메소드를 호출할 때, HttpServletRequest 객체를 인자로 전달한다.  
서블릿 컨테이너가 서블릿 객체를 생성하고, `service()`, `doGet()`, `doPost()` 메소드도 호출한다. 이 메소드들이 정상적으로 호출되기 위해서는 HttpServletRequest와 HttpServletResponse 객체가 필요한데, 이 객체들도 서블릿 컨테이너가 생성한다. 즉, `service()` 메소드는 매개변수로 받은 HttpServletRequest 객체를 통해 다양한 요청을 처리한다.  
**스프링 컨테이너**는 클라이언트가 서버에 "insertBoard.do" 요청을 전달하면, `@Controller`가 붙은 모든 컨트롤러 객체를 생성하고 `insertBoard()` 메소드를 실행한다. 그러면 매개변수에 해당하는 BoardVO 객체를 생성하고, 사용자가 입력한 파라미터(title, writer 등) 값들을 추출하여 BoardVO 객체에 저장한다. 이 때, BoardVO 클래스의 Setter 메소드들이 호출된다. `insertBoard()` 메소드를 호출할 때, 사용자 입력값들이 설정된 BoardVO 객체가 인자로 전달된다. 여기서 insertBoard.jsp의 \<form> 태그 속 name 파라미터 값과 Command 객체의 Setter 메소드 이름이 같아야만, Setter 인젝션에 의해 자동으로 입력 값이 저장된다.  

### 요청 방식에 따른 처리
`@RequestMapping`을 이용하면 클라이언트의 요청 방식(GET, POST)에 따라 수행될 메소드를 다르게 설정할 수 있다. 해당 어노테이션에 `method` 파라미터를 설정하여 요청이 GET인지 POST인지에 따라 행동을 다르게 할 수 있는 것이다. 클라이언트가 URL을 입력하거나 하이퍼링크를 클릭하여 요청하면 기본적으로 **GET 방식**으로 요청이 되고, 로그인 버튼을 통해 "/login.do" 요청이 서버에 전송되는 것은 **POST 방식**의 요청이다.  
JSP 파일에 Command 객체의 변수를 사용하려면 `${...}` 구문을 사용할 수 있는데, 이름은 기본적으로 클래스 이름의 첫 글자가 소문자로 한다. 하지만 이 이름을 `@ModelAttribute`를 이용하여 따로 지정할 수 있기도 하다.  

### Controller 리턴 타입
Controller 리턴 타입을 String으로 설정하면 View 이름을 문자열로 리턴하겠다는 것이고, ModelAndView로 설정하면 검색된 Model 데이터와 View 이름을 모두 저장하여 리턴하겠다는 의미이다.  
리턴타입을 String으로 하여 View 이름이 문자열로 리턴되면 스프링 컨테이너는 리턴된 JSP 파일을 찾아 실행한다. 매개변수도 ModelAndView에서 Model로 변경되었는데, 리턴된 JSP 화면에서 검색 결과를 출력하기 위해 Model에 저장된 데이터를 사용한다.  

### 기타 어노테이션
`@RequestParam`을 이용하면 Command 클래스에는 없는 파라미터 정보를 추출할 수 있다. 추출하고자 하는 파라미터에 해당하는 변수와 Setter 메소드가 Command 클래스에 선언되어 있지 않을 때 사용한다.  
`@ModelAttribute`는 Command 객체의 이름을 변경할 목적으로 사용할 수도 있고, View에서 사용할 데이터를 설정하는 용도로도 사용할 수 있다. @ModelAttribute가 설정된 메소드는 @RequestMapping 어노테이션이 적용된 메소드보다 먼저 호출되고, @ModelAttribute 메소드의 실행 결과로 리턴된 객체를 View 페이지에서 사용할 수 있다.  
`@SessionAttributes`는 수정 작업을 처리할 때, 유용하게 사용할 수 있다. 글 수정 버튼을 누르면 제목과 내용 정보를 가지고 `updateBoard()`를 행하는데, 작성자(writer) 정보는 넘어가지 않고 있다. 만약, SQL 구문이 작성자 컬럼까지 수정하도록 되어 있을 때에 파라미터를 넘기지 않으면 **null**로 바뀐다. 이런 문제를 해결하기 위해 @SessionAttributes를 사용한다.  

```java
@Controller
@SessionAttributes("board")
public class BoardController {
    @RequestMapping("/updateBoard.do")
    public String updateBoard(@ModelAttribute("board") BoardVO vo, BoardDAO boardDAO) {
        boardDAO.updateBoard(vo);
        return "getBoardList.do";
    }

    @RequestMapping("/getBoard.do")
    public String getBoard(BoardVO vo, BoardDAO boardDAO, Model model) {
        model.addAttribute("board", boardDAO.getBoard(vo));
        return "getBoard.jsp";
    }
}
```
`getBoard()` 메소드는 검색 결과인 BoardVO 객체를 board라는 이름으로 Model에 저장하게 된다. 이 때, @SessionAttributes에 의해 "board"라는 이름으로 저장되는 Model 데이터를 세션에도 자동으로 저장하게 된다. `updateBoard()`의 파라미터에도 @ModelAttribute가 선언되어, 사용자가 입력하지 않은 데이터는 검색 결과를 따르게 되고 나머지는 입력한 데이터로 수정된다.  

### 프레젠테이션 레이어와 비즈니스 레이어 통합
브라우저에서 버튼이나 하이퍼링크를 클릭하여 서버에 요청을 전송하면, 모든 요청을 DispatcherServlet이 받아서 Controller에게 요청을 전달한다. Controller는 매개변수를 통해 전달된 DAO 객체를 이용하여 사용자가 요청한 로직을 처리한다.  
하지만 **Controller는 DAO 객체를 직접 이용해서는 안되고**, 반드시 **비즈니스 컴포넌트를 이용**해야 한다. 이때 컴포넌트가 제공하는 Service 인터페이스를 이용한다. **유지보수** 과정에서 DAO 클래스를 다른 클래스로 쉽게 교체하기 위한 것이다. 클라이언트가 인터페이스를 통해서 비즈니스 컴포넌트를 이용하면 컴포넌트의 구현 클래스를 수정하거나 다른 클래스로 대체하더라도, 이를 사용하는 클라이언트는 수정하지 않아도 되기 때문이다.  
BoardController의 모든 메소드에 대해 boardService 변수를 이용하게 하여 비즈니스 컴포넌트를 사용하도록 수정했다. 또, @Autowired로 선언하여 BoardServiceImpl 객체가 의존성 주입된다. 이로 인해, BoardServiceImpl에서 사용하는 DAO 클래스가 변경되어도 클라이언트에 해당하는 BoardController는 수정할 필요가 없게 된다.  
Controller에서 DAO 메소드를 직접 호출하면 안되는 이유로는 **AOP**도 있다. 횡단 관심에 해당하는 어드바이스가 동작하려면 반드시 Service 구현 클래스(ServiceImpl)의 비즈니스 메소드가 실행되어야 한다. 포인트컷을 설정할 때 ServiceImpl의 메소드로 설정했기 때문에, DAO의 메소드를 직접 호출하면 어드바이스가 동작하지 않게 된다.  
클라이언트로부터 ".do" 요청이 들어오면 서블릿 컨테이너는 DispatcherServlet을 생성하고, 서블릿은 스프링 설정 파일 presentation-layer.xml을 로딩하여 스프링 컨테이너를 구동한다. 스프링 설정 파일은 Controller 객체들만 컴포넌트 스캔하도록 설정해 BoardController 객체만 메모리에 생성을 시도한다. 하지만, @Autowired 어노테이션을 사용하기 위해서는 **의존성 주입될 BoardServiceImpl 객체가 미리 생성**되어 있어야 하므로 에러가 발생하게 된다. 이러한 비즈니스 컴포넌트를 먼저 생성하는 다른 스프링 컨테이너가 필요하다.  
Controller 객체들이 생성되기 전에 비즈니스 컴포넌트들을 메모리에 생성시키는 ContextLoaderListener를 사용할 수 있다. ContextLoaderListener 클래스는 서블릿 컨테이너가 web.xml 파일을 읽어서 구동될 때, 자동으로 메모리에 생성되는 pre-loading 객체다.  
톰캣 서버를 구동하면, web.xml 파일을 로딩하여 서블릿 컨테이너가 구동된다. 서블릿 컨테이너는 web.xml 파일에 등록된 ContextLoaderListener 객체를 생성(pre-loading)하고, Listener 객체는 applicationContext.xml 파일을 로딩하여 루트 컨테이너가 되는 스프링 컨테이너를 구동한다. 이때, Service 구현 클래스나 DAO 객체들이 메모리에 생성된다. 사용자가 버튼을 통해 ".do" 요청을 서버에 전달하면 서블릿 컨테이너는 DispatcherServlet 객체를 생성하고, Servlet 객체는 presentation-layer.xml 파일을 로딩하여 다른 스프링 컨테이너를 구동한다. 이 두 번째 스프링 컨테이너가 Controller 객체를 메모리에 생성한다.  


### 커밋
- [0b5c25f](https://github.com/ItzTree/study-archive/commit/0b5c25f50b490681058e7872f15dc9e860d5c3b0)  
    기존의 Spring MVC를 어노테이션 @Controller와 @RequestMapping을 이용하여 리팩토링한다.  
- [eec8f5c](https://github.com/ItzTree/study-archive/commit/eec8f5c3997797176c6c563de045ed1212d0f982)  
    Controller를 하나로 통합하고, 사용자 이름 정보를 세션에 저장한다. 또, Controller 리턴 타입을 String으로 통일한다.  
- [b926de5](https://github.com/ItzTree/study-archive/commit/b926de501d685bfd1fb81aa3fbd5c3fa203fcf45)  
    프레젠테이션 레이어와 비즈니스 레이어를 통합한다.  
