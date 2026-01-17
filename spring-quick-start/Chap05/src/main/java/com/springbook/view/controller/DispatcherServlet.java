package com.springbook.view.controller;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.impl.BoardDAO;
import com.springbook.biz.user.UserVO;
import com.springbook.biz.user.impl.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        process(request, response);
    }

    // GET이든 POST든 이 메서드에서 모두 처리
    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 클라이언트의 요청 path 정보를 추출한다.
        String uri = request.getRequestURI();
        String path = uri.substring(uri.lastIndexOf("/"));
        System.out.println(path);

        // 2. 클라이언트의 요청 path에 따라 적절히 분기처리 한다.
        if (path.equals("/login.do")) {
            System.out.println("로그인 처리");

            // 1. 사용자 입력 정보 추출
            String id = request.getParameter("id");
            String password = request.getParameter("password");

            // 2. DB 연동 처리
            UserVO vo = new UserVO();
            vo.setId(id);
            vo.setPassword(password);

            UserDAO userDAO = new UserDAO();
            UserVO user = userDAO.getUser(vo);

            // 3. 화면 네비게이션
            if (user != null) {
                response.sendRedirect("getBoardList.do");
            } else {
                response.sendRedirect("login.jsp");
            }
        } else if (path.equals("/logout.do")) {
            System.out.println("로그아웃 처리");

            // 1. 세션 연결 종료 (로그아웃 처리)
            HttpSession session = request.getSession();
            session.invalidate();

            // 2. 로그인 화면으로 이동
            response.sendRedirect("login.jsp");
        } else if (path.equals("/insertBoard.do")) {
            System.out.println("글 등록 처리");

            // 1. 요청 데이터 인코딩 설정 (한글 처리를 위해 필수)
//            request.setCharacterEncoding("UTF-8");

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
            response.sendRedirect("getBoardList.do");
        } else if (path.equals("/updateBoard.do")) {
            System.out.println("글 수정 처리");

            // 1. 요청 데이터 인코딩 설정
//            request.setCharacterEncoding("UTF-8");

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
            response.sendRedirect("getBoardList.do");
        } else if (path.equals("/deleteBoard.do")) {
            System.out.println("글 삭제 처리");

            // 1. 파라미터 추출 (글 번호)
            String seq = request.getParameter("seq");

            // 2. VO 객체 생성 및 값 설정
            BoardVO vo = new BoardVO();
            vo.setSeq(Integer.parseInt(seq));

            // 3. DAO 객체 생성 및 deleteBoard() 호출
            BoardDAO boardDAO = new BoardDAO();
            boardDAO.deleteBoard(vo);

            // 4. 글 목록 화면으로 이동
            response.sendRedirect("getBoardList.do");
        } else if (path.equals("/getBoard.do")) {
            System.out.println("글 상세 조회 처리");

            // 1. 검색할 게시글 번호 추출
            String seq = request.getParameter("seq");

            // 2. DB 연동 처리
            BoardVO vo = new BoardVO();
            vo.setSeq(Integer.parseInt(seq));

            BoardDAO boardDAO = new BoardDAO();
            BoardVO board = boardDAO.getBoard(vo);

            // 3. 검색 결과를 세션에 저장하고 상세 화면으로 이동한다
            HttpSession session = request.getSession();
            session.setAttribute("board", board);
            response.sendRedirect("getBoard.jsp");
        } else if (path.equals("/getBoardList.do")) {
            System.out.println("글 목록 검색 처리");

            // 1. 사용자 입력 정보 추출(검색 기능은 나중에 구현)
            request.setCharacterEncoding("UTF-8");

            // 2. DB 연동 처리
            BoardVO vo = new BoardVO();
            BoardDAO boardDAO = new BoardDAO();
            List<BoardVO> boardList = boardDAO.getBoardList(vo);

            // 3. 검색 결과를 세션에 저장하고 목록 화면으로 이동한다
            HttpSession session = request.getSession();
            session.setAttribute("boardList", boardList);
            response.sendRedirect("getBoardList.jsp");
        }
    }
}