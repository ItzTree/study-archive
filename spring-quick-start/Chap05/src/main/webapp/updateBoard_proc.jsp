<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.springbook.biz.board.impl.BoardDAO" %>
<%@ page import="com.springbook.biz.board.BoardVO" %>

<%
    // 1. 요청 데이터 인코딩 설정
    request.setCharacterEncoding("UTF-8");

    // 2. 파라미터 추출
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String seq = request.getParameter("seq");

    // 3. VO 객체 생성 및 값 설정
    BoardVO vo = new BoardVO();
    vo.setTitle(title);
    vo.setContent(content);
    vo.setSeq(Integer.parseInt(seq));

    // 4. DAO 객체 생성 및 updateBoard() 호출
    BoardDAO boardDAO = new BoardDAO();
    boardDAO.updateBoard(vo);

    // 5. 글 목록 화면으로 이동
    response.sendRedirect("getBoardList.jsp");
%>