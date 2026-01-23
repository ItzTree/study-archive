<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    ${userName}님! 게시판에 오신걸 환영합니다...
    <a href="logout.do">Log-out</a>
  </h3>

  <!-- 검색 폼 -->
  <form action="getBoardList.do" method="post">
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
    <c:forEach items="${boardList}" var="board">
    <tr>
      <td>${board.seq}</td>
      <td align="left">
        <!-- 제목 클릭 시 상세 페이지(getBoard.jsp)로 이동 -->
        <a href="getBoard.do?seq=${board.seq}">
            ${board.title}
        </a>
      </td>
      <td>${board.writer}</td>
      <td>${board.regDate}</td>
      <td>${board.cnt}</td>
    </tr>
    </c:forEach>

  </table>
  <br>
  <a href="insertBoard.jsp">새글 등록</a>
</center>
</body>
</html>