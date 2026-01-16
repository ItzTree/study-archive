<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.springbook.biz.board.impl.BoardDAO" %>
<%@ page import="com.springbook.biz.board.BoardVO" %>

<%
  // 1. 요청 데이터 인코딩 설정 (한글 처리를 위해 필수)
  request.setCharacterEncoding("UTF-8");

  // 2. 사용자 입력 정보 추출
  String title = request.getParameter("title");
  String writer = request.getParameter("writer");
  String content = request.getParameter("content");

  // 3. VO 객체 생성 및 값 설정
  BoardVO vo = new BoardVO();
  vo.setTitle(title);
  vo.setWriter(writer);
  vo.setContent(content);

  // 4. DAO 객체 생성 및 insertBoard() 호출
  BoardDAO boardDAO = new BoardDAO();
  boardDAO.insertBoard(vo);

  // 5. 글 목록 화면으로 이동
  response.sendRedirect("getBoardList.jsp");
%>