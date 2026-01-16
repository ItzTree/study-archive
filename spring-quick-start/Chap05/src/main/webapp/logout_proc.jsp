<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 1. 세션 연결 종료 (로그아웃 처리)
    session.invalidate();

    // 2. 로그인 화면으로 이동
    response.sendRedirect("login.jsp");
%>