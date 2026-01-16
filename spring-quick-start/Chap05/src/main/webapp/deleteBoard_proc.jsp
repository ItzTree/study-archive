<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.springbook.biz.board.impl.BoardDAO" %>
<%@ page import="com.springbook.biz.board.BoardVO" %>

<%
  // 1. 파라미터 추출 (글 번호)
  String seq = request.getParameter("seq");

  // 2. VO 객체 생성 및 값 설정
  BoardVO vo = new BoardVO();
  vo.setSeq(Integer.parseInt(seq));

  // 3. DAO 객체 생성 및 deleteBoard() 호출
  BoardDAO boardDAO = new BoardDAO();
  boardDAO.deleteBoard(vo);

  // 4. 글 목록 화면으로 이동
  response.sendRedirect("getBoardList.jsp");
%>