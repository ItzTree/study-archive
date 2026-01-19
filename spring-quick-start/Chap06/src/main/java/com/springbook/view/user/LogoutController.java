package com.springbook.view.user;

import com.springbook.view.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController implements Controller {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("로그아웃 처리");

        // 1. 세션 연결 종료 (로그아웃 처리)
        HttpSession session = request.getSession();
        session.invalidate();

        // 2. 로그인 화면으로 이동
        return "login";
    }
}
