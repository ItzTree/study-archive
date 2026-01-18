# Chap05

책의 Day 3 CLASS 01 ~ Day 3 CLASS 03 파트를 다룬다.  

### Model 1 아키텍처 구조
Model 1 아키텍처는 JSP와 JavaBeans만 사용하여 웹을 개발했다. 여기서 살펴볼 것은 **Model** 기능의 JavaBeans다. Model의 정확한 의미는 **데이터베이스 연동 로직을 제공**하면서 **DB에서 검색한 데이터가 저장되는 자바 객체**다. VO, DAO 클래스가 바로 Model 기능의 자바 객체에 해당한다.  
Model 1 아키텍처에서는 JSP 파일이 가장 중요한 역할을 수행하는데, 이 파일이 C**ontroller와 View** 기능을 모두 처리하기 때문이다. Controller 기능은 **JSP 파일에 작성된 사용자의 요청 처리와 관련된 자바 코드**를 의미하고, **마크업 언어인 HTML과 CSS**가 View 기능을 담당한다.  
JSP 파일에 자바 코드와 마크업 코드들이 뒤섞여 있어 어려움이 생겼고, Model 2인 MVC 아키텍처가 등장했다. Model, View, Controller로 기능을 분리한 것이다.  

화면 내비게이션 방법에는 포워드 방식과 리다이렉트 방식이 있다. **포워드**는 RequestDispatcher를 이용하여 응답으로 사용할 JSP 화면으로 넘겨서, 포워드된 화면이 클라이언트에 전송되는 방식이다. 한 번의 요청과 응답으로 처리되어 실행 속도는 빠르지만, 클라이언트 브라우저에서 URL이 바뀌지 않아 어디에서 들어왔는지를 확인할 수 없다.  
**리다이렉트**는 요청된 JSP에서 일단 브라우저로 응답 메시지를 보냈다가 다시 서버로 재요청하는 방식이다. 응답이 들어온 파일로 브라우저의 URL이 변경되지만, 두 번의 요청과 응답으로 처리되어 실행 속도는 포워드보다 느리다.  

- **login.jsp**  
    사용자가 id와 password 파라미터에 해당하는 값을 입력하고 로그인 버튼을 누르면, id와 password 정보를 가지고 `login_proc.jsp` 파일을 호출한다.  
- **login_proc.jsp**  
    사용자가 입력한 아이디와 비밀번호를 request 객체로부터 추출한다. UserVO와 UserDAO 객체를 이용하여 사용자 정보를 검색하고, 로그인에 성공하면 `getBoardList.jsp`로 이동하고 실패하면 재로그인을 위해 `login.jsp`로 이동한다.  
- **getBoardList.jsp**  
    BoardVO와 BoardDAO 객체를 이용하여 BOARD 테이블에 있는 게시글 목록을 검색한다. 검색 결과로 얻은 List\<BoardVO> 객체를 이용하여 게시글 목록 화면을 구성한다. 게시글 제목을 클릭했을 때, 게시글의 상세 정보를 조회하여 출력하기 위해 `getBoard.jsp` 파일로 링크를 연결했다. 이 때, 사용자가 클릭한 게시글 번호를 넘겨주기 위해 `?`를 추가하고 쿼리 문자열 정보를 같이 넘겨준다.  
- **getBoard.jsp**  
    글 목록 화면에서 사용자가 클릭한 게시글 번호를 추출한다. BoardDAO 객체의 `getBoard()` 메소드를 이용하여 이 게시글 번호에 해당하는 BoardVO 객체를 검색하고, 이 객체의 값들을 화면에 출력한다. 상세 화면은 수정을 위한 화면이기도 하다.  
- **insertBoard.jsp**  
    글 등록 화면은 로그인 화면과 유사하다. title, writer, content 파라미터 정보를 입력하고 버튼을 누르면 `insertBoard_proc.jsp` 파일을 호출한다.  
- **insertBoard_proc.jsp**  
    사용자가 입력한 데이터를 데이터베이스에 저장한다. 사용자 입력 정보를 추출하기 전에 `setCharacterEncoding()` 메소드로 한글 인코딩을 처리한다. 추출한 입력값들을 BoardVO 객체에 저장하고 BoardDAO의 `insertBoard()` 메소드를 호출하여 DB에 연동한다.  
- **updateBoard_proc.jsp**  
    글 수정을 처리하려면 글의 제목과 내용, 게시글 번호를 알아야 한다. `getBoard.jsp`에 hidden 타입의 \<input> 태그를 추가하여 게시글 번호도 같이 전달한다. 사용자가 제목과 내용을 수정하고 글 수정 버튼을 클릭하면 title, content 파라미터 정보와 hidden으로 설정된 게시글 번호 정보를 갖고 `updateBoard_proc.jsp` 파일을 호출한다.  
- **deleteBoard_proc.jsp**  
    삭제 요청된 게시글 번호를 추출하여 BoardVO 객체에 저장한다. 이후 BoardDAO의 `deleteBoard()` 메소드를 호출하여 데이터를 삭제 처리한다.  
- **logout_proc.jsp**  
    세션과 관련된 작업을 처리해야 하지만, 단순히 로그아웃에 대한 세션을 종료하고, 로그인 화면으로 이동한다.  

### Model 2 아키텍처 구조
Model 1 아키텍처는 자바 로직과 화면 디자인이 통합되어 유지보수가 어렵다. JSP가 담당했던 Controller 로직이 별도의 서블릿으로 옮겨지면서 Model 2 아키텍처가 된다. 
| 기능 | 구성 요소 | 개발 주체 |
| --- | --- | --- |
| Model | VO, DAO 클래스 | 자바 개발자 |
| View | JSP 페이지 | 웹 디자이너 |
| Controller | Servlet 클래스 | 자바 개발자 or MVC 프레임워크 |

Controller는 MVC 프레임워크가 제공하는 것을 사용하는 것이 더 효율적이고 안정적이지만, 구조가 복잡하고 어렵기 때문에 지금 당장은 사용하지 않겠다.  

`DispatcherSevlet`에는 GET 방식 요청을 처리하는 `doGet()` 메소드와 POST를 처리하는 `doPost()` 메소드가 있다. 두 메소드 모두 `process()` 메소드를 통해 클라이언트의 요청을 처리한다. `process()` 메소드는 클라이언트의 요청 URI로부터 path 정보를 추출하여 분기 처리 로직을 실행한다.  

- **login.do**  
    `*.do` 형태의 요청에 대해서만 DispatcherServlet이 동작하므로 login.jsp 파일에서 \<form> 엘리먼트의 action 속성값을 login.do로 고친다.  
- **getBoardList.do**  
    글 목록 화면을 처리했던 getBoardList.jsp 파일에서 Controller 로직에 해당하는 자바 코드를 DispatcherServlet으로 복사한다. Model 1에서는 getBoardList.jsp 화면에서 검색 결과를 출력하기 위해 세션 객체(`HttpSession`)를 사용했었는데, 이는 사용자가 많아질수록 서버에 부담이 된다.  
    따라서 검색 결과는 세션이 아닌 `HttpServletRequest` 객체에 저장하여 공유한다. 이 객체는 클라이언트가 서버에 요청을 전송할 때마다 매번 새롭게 생성되고, 응답 메시지가 브라우저에 전송되면 삭제되는 1회성 객체이므로 서버에 부담이 가지 않는다.  
    이제 `getBoardList.jsp`는 세션에 저장된 글 목록을 꺼내서 출력하는 기능만 제공한다. 이를 위해서는 DispatcherServlet이 먼저 실행되어 검색 결과를 세션에 저장되어야 하므로, 브라우저는 `getBoardList.do`로 요청해야 한다. 즉, DispatcherServlet이 클라이언트의 `"/getBoardList.do"` 요청을 받으면 `BoardDAO` 객체를 이용하여 글 목록을 검색하고 세션에 등록한다. 이 후 `getBoardList.jsp` 화면을 요청하면 세션에 있는 글 목록을 가지고 화면을 구성하는 것이다.  
- **insertBoard.do**  
    `insertBoard_proc.jsp` 파일에 있던 자바 코드를 복사하여 DispatcherServlet 클래스에 붙여넣는다. 등록 작업이 성공하면 반드시 `getBoardList.do`를 요청하도록 해야 한다. `getBoardList.jsp`를 요청하게 되면 글을 등록하기 전의 세션에 있는 글 목록을 가져오기 때문에, `getBoardList.do`를 통해 세션을 최신화해야 한다. 이는 수정, 삭제 작업에서도 마찬가지이다.  

### 커밋
- [96faab6](https://github.com/ItzTree/study-archive/commit/96faab66bcbac787d9f4daf96d28a71a344cc5e0)  
    Model 1 아키텍처를 적용하여 글 목록 검색 기능을 구현하였다.  
- [7509892](https://github.com/ItzTree/study-archive/commit/7509892dab59e1f5bd9db09b61febbccb3d57174)  
    글 내용 조회하는 기능을 구현한다.  
- [9cab019](https://github.com/ItzTree/study-archive/commit/9cab019f3a8754391db01dcb3aeccc9267efe52e)  
    Model 1 아키텍처를 적용하여 게시글 서비스를 구현한다.  
- [22018e6](https://github.com/ItzTree/study-archive/commit/22018e61e0b27c7541063ae07559ae3cbbc340df)  
    Model 2 아키텍처를 적용한다.  