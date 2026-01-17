<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 아래 import 구문들이 꼭 있어야 자바 클래스를 불러올 수 있습니다 -->
<%@ page import="java.util.List" %>
<%@ page import="com.springbook.biz.board.impl.BoardDAO" %>
<%@ page import="com.springbook.biz.board.BoardVO" %>

<%
  // 세션에 저장된 글 목록을 꺼낸다.
  List<BoardVO> boardList = (List<BoardVO>) session.getAttribute("boardList");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>글 목록</title>
</head>
<body>
<center>
  <h1>글 목록</h1>
  <h3>
    테스트님 환영합니다...
    <a href="logout.do">Log-out</a>
  </h3>

  <!-- 검색 폼 -->
  <form action="getBoardList.jsp" method="post">
    <table border="1" cellpadding="0" cellspacing="0" width="700">
      <tr>
        <td align="right">
          <select name="searchCondition">
            <option value="TITLE">제목</option>
            <option value="CONTENT">내용</option>
          </select>
          <input type="text" name="searchKeyword"/>
          <input type="submit" value="검색"/>
        </td>
      </tr>
    </table>
  </form>

  <!-- 목록 출력 테이블 -->
  <table border="1" cellpadding="0" cellspacing="0" width="700">
    <tr>
      <th bgcolor="orange" width="100">번호</th>
      <th bgcolor="orange" width="200">제목</th>
      <th bgcolor="orange" width="150">작성자</th>
      <th bgcolor="orange" width="150">등록일</th>
      <th bgcolor="orange" width="100">조회수</th>
    </tr>

    <%-- 자바 코드로 리스트 반복 출력 --%>
    <% for (BoardVO board : boardList) { %>
    <tr>
      <td><%= board.getSeq() %></td>
      <td align="left">
        <!-- 제목 클릭 시 상세 페이지(getBoard.jsp)로 이동 -->
        <a href="getBoard.do?seq=<%= board.getSeq() %>">
          <%= board.getTitle() %>
        </a>
      </td>
      <td><%= board.getWriter() %></td>
      <td><%= board.getRegDate() %></td>
      <td><%= board.getCnt() %></td>
    </tr>
    <% } %>

  </table>
  <br>
  <a href="insertBoard.jsp">새글 등록</a>
</center>
</body>
</html>