# Chap07

책의 Day 4 CLASS 01 ~ Day 4 CLASS 03 파트를 다룬다.  

### 어노테이션 기반 MVC
기존에는 스프링 컨테이너가 Controller 클래스를 생성하게 하려면 모두 bean 등록해야 했지만, 어노테이션을 사용하면 클래스 선언부 위에 `@Controller`를 붙이기만 하면 된다. 여기서 어노테이션을 통해 Controller로 인식하게 할 수는 있지만 `/insertBoard.do` 요청에 대해 `insertBoard()` 메소드가 실행되도록 할 수는 없다.  
그래서 HandlerMapping을 대신할 `@RequestMapping`을 사용한다.  
대부분 Controller는 사용자의 입력 정보를 추출하여 VO 객체에 저장한다. 그리고 비즈니스 컴포넌트의 메소드를 호출할 때 VO 객체를 인자로 전달한다. 사용자의 입력 정보가 추가가 될 때마다 기존의 로직에서는 insert, update, delete Controller 클래스를 매번 수정해야 한다. 하지만 **Command 객체**(Controller 메소드 매개변수로 받은 VO 객체를 지칭)를 이용하면 사용자 입력 정보 추출과 VO 객체 생성, 값 설정을 모두 컨테이너가 자동으로 처리한다.  
**서블릿 컨테이너**는 클라이언트의 HTTP 요청이 서버에 전달되는 순간, HttpServletRequest 객체를 생성하고 HTTP 프로토콜에 설정된 모든 정보를 추출하여 HttpServletRequest 객체에 저장한다. DispatcherServlet이 `service()` 메소드를 호출할 때, HttpServletRequest 객체를 인자로 전달한다.  
서블릿 컨테이너가 서블릿 객체를 생성하고, `service()`, `doGet()`, `doPost()` 메소드도 호출한다. 이 메소드들이 정상적으로 호출되기 위해서는 HttpServletRequest와 HttpServletResponse 객체가 필요한데, 이 객체들도 서블릿 컨테이너가 생성한다. 즉, `service()` 메소드는 매개변수로 받은 HttpServletRequest 객체를 통해 다양한 요청을 처리한다.  
**스프링 컨테이너**는 클라이언트가 서버에 "insertBoard.do" 요청을 전달하면, `@Controller`가 붙은 모든 컨트롤러 객체를 생성하고 `insertBoard()` 메소드를 실행한다. 그러면 매개변수에 해당하는 BoardVO 객체를 생성하고, 사용자가 입력한 파라미터(title, writer 등) 값들을 추출하여 BoardVO 객체에 저장한다. 이 때, BoardVO 클래스의 Setter 메소드들이 호출된다. `insertBoard()` 메소드를 호출할 때, 사용자 입력값들이 설정된 BoardVO 객체가 인자로 전달된다. 여기서 insertBoard.jsp의 \<form> 태그 속 name 파라미터 값과 Command 객체의 Setter 메소드 이름이 같아야만, Setter 인젝션에 의해 자동으로 입력 값이 저장된다.  



### 커밋
- 