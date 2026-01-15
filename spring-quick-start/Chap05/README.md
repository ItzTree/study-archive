# Chap05

책의 Day 3 CLASS 01 ~ Day 3 CLASS 03 파트를 다룬다.  

### Model 1 아키텍처 구조
Model 1 아키텍처는 JSP와 JavaBeans만 사용하여 웹을 개발했다. 여기서 살펴볼 것은 Model 기능의 JavaBeans다. Model의 정확한 의미는 데이터베이스 연동 로직을 제공하면서 DB에서 검색한 데이터가 저장되는 자바 객체다. VO, DAO 클래스가 바로 Model 기능의 자바 객체에 해당한다.  
Model 1 아키텍처에서는 JSP 파일이 가장 중요한 역할을 수행하는데, 이 파일이 Controller와 View 기능을 모두 처리하기 때문이다. Controller 기능은 JSP 파일에 작성된 사용자의 요청 처리와 관련된 자바 코드를 의미하고, 마크업 언어인 HTML과 CSS가 View 기능을 담당한다.  
JSP 파일에 자바 코드와 마크업 코드들이 뒤섞여 있어 어려움이 생겼고, Model 2인 MVC 아키텍처가 등장했다. Model, View, Controller로 기능을 분리한 것이다.  

화면 내비게이션 방법에는 포워드 방식과 리다이렉트 방식이 있다. 포워드는 RequestDispatcher를 이용하여 응답으로 사용할 JSP 화면으로 넘겨서, 포워드된 화면이 클라이언트에 전송되는 방식이다. 한 번의 요청과 응답으로 처리되어 실행 속도는 빠르지만, 클라이언트 브라우저에서 URL이 바뀌지 않아 어디에서 들어왔는지를 확인할 수 없다.  
리다이렉트는 요청된 JSP에서 일단 브라우저로 응답 메시지를 보냈다가 다시 서버로 재요청하는 방식이다. 응답이 들어온 파일로 브라우저의 URL이 변경되지만, 두 번의 요청과 응답으로 처리되어 실행 속도는 포워드보다 느리다.  

- login.jsp  
    사용자가 id와 password 파라미터에 해당하는 값을 입력하고 로그인 버튼을 누르면, id와 password 정보를 가지고 `login_proc.jsp` 파일을 호출한다.  
- login_proc.jsp  
    사용자가 입력한 아이디와 비밀번호를 request 객체로부터 추출한다. UserVO와 UserDAO 객체를 이용하여 사용자 정보를 검색하고, 로그인에 성공하면 `getBoardList.jsp`로 이동하고 실패하면 재로그인을 위해 `login.jsp`로 이동한다.  
- getBoardList.jsp  
    BoardVO와 BoardDAO 객체를 이용하여 BOARD 테이블에 있는 게시글 목록을 검색한다. 검색 결과로 얻은 List\<BoardVO> 객체를 이용하여 게시글 목록 화면을 구성한다. 게시글 제목을 클릭했을 때, 게시글의 상세 정보를 조회하여 출력하기 위해 `getBoard.jsp` 파일로 링크를 연결했다. 이 때, 사용자가 클릭한 게시글 번호를 넘겨주기 위해 `?`를 추가하고 쿼리 문자열 정보를 같이 넘겨준다.  
- getBoard.jsp  
    글 목록 화면에서 사용자가 클릭한 게시글 번호를 추출한다. BoardDAO 객체의 `getBoard()` 메소드를 이용하여 이 게시글 번호에 해당하는 BoardVO 객체를 검색한다. 이 객체의 값들을 화면에 출력한다. 상세 화면은 수정을 위한 화면이기도 하다.  



### 커밋
- [96faab6](https://github.com/ItzTree/study-archive/commit/96faab66bcbac787d9f4daf96d28a71a344cc5e0)  
    Model 1 아키텍처를 적용하여 글 목록 검색 기능을 구현하였다.  
-  