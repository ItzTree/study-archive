package com.springbook.biz; // 본인 패키지명

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/") // 주소창에 localhost:8080/ 입력 시 들어옴
    public String home() {
        System.out.println("컨트롤러가 호출되었습니다!"); // 서버 로그 확인용
        return "home"; // /WEB-INF/views/home.jsp를 찾아가라!
    }
}