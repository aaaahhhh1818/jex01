package org.zerock.jex01.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.board.service.BoardService;
import org.zerock.jex01.board.service.TimeService;

@Controller
@RequestMapping("/board/*")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final TimeService timeService; //autowired대신 final-> 인터페이스만 바라보게

    private final BoardService boardService; //postMapping에서 사용

    @GetMapping("/time")
    public void getTime(Model model){
        log.info("==================controller getTime===================");
        model.addAttribute("time", timeService.getNow());
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO){ // 리다이렉트 하려고 String사용

        log.info("boardDTOM           " + boardDTO);

        Long bno = boardService.register(boardDTO);

        return "redirect:/board/list";
    }

}
