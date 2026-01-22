package com.springbook.view.board;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.impl.BoardDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GetBoardController  {
    @RequestMapping("/getBoard.do")
    public ModelAndView getBoard(BoardVO vo, BoardDAO boardDAO, ModelAndView mav) {
        mav.addObject("board", boardDAO.getBoard(vo));
        mav.setViewName("getBoard.jsp");
        return mav;
    }
}
