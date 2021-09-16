package org.zerock.jex01.board.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.board.service.BoardService;
import org.zerock.jex01.board.service.TimeService;
import org.zerock.jex01.common.dto.PageMaker;
import org.zerock.jex01.common.dto.PageRequestDTO;
import org.zerock.jex01.common.dto.PageResponseDTO;
import sun.jvm.hotspot.debugger.Page;

@Controller
@RequestMapping("/board/*")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final TimeService timeService; //autowired대신 final-> 인터페이스만 바라보게

    private final BoardService boardService; //postMapping에서 사용

    @GetMapping("/time")
    public void getTime(int num, Model model){
        log.info("==================controller getTime===================");
        model.addAttribute("time", timeService.getNow());

        log.info(num % 0);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/register")
    public void registerGet() {

    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes){ // 리다이렉트 하려고 String사용

        log.info("boardDTOM           " + boardDTO);

        Long bno = boardService.register(boardDTO);

        log.info("==================c     registerPost===================");
        log.info(bno);
        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) { //그냥 리스트 뽑아주는거라서 void //Model 사용해서 JSP에 담아서 보냄

        log.info("c    getList................" + pageRequestDTO); //controller로그 c, service로그 s

        log.info("====================================");
        log.info(boardService);
        log.info("====================================");

        //model.addAttribute("dtoList", boardService.getDTOList(pageRequestDTO));

        PageResponseDTO<BoardDTO> responseDTO = boardService.getDTOList(pageRequestDTO);

        model.addAttribute("dtoList", responseDTO.getDtoList());

        int total = responseDTO.getCount();
        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();

        model.addAttribute("pageMaker", new PageMaker(page, size, total));

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = {"/read", "/modify", "/read2"})
    public void read(Long bno, PageRequestDTO pageResponseDTO, Model model) {

        log.info("c     read " + bno);
        log.info("c     read " + pageResponseDTO);

        model.addAttribute("boardDTO", boardService.read(bno));

    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("c     remove: " + bno);

        if(boardService.remove(bno)) {
            log.info(bno);
            redirectAttributes.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @PreAuthorize("principal.mid == #boardDTO.writer") //얘네가 맞아야 글을 수정할 수 있음
    @PostMapping("/modify")
    public String modify(BoardDTO boardDTO, PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {

        log.info("--------------------modify-------------");
        log.info("--------------------modify-------------");
        log.info("--------------------modify-------------");

        log.info("c        modify: " + boardDTO);
//        if(boardDTO.getFiles().size() > 0) {
//            boardDTO.getFiles().forEach(dto -> log.info(dto)); //수정된 첨부파일이 포함된 수 만큼 정보 로그 찍기
//        }

        log.info("--------------------modify-------------");
        log.info("--------------------modify-------------");
        log.info("--------------------modify-------------");

        if(boardService.modify(boardDTO)) {
            redirectAttributes.addFlashAttribute("result", "modified"); //flash 하면 눈에 안보임
        }
        redirectAttributes.addAttribute("bno", boardDTO.getBno()); //bno 값을 가져와줌
        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("size", pageRequestDTO.getSize());

        if(pageRequestDTO.getType() != null) {
            redirectAttributes.addAttribute("type", pageRequestDTO.getType());
            redirectAttributes.addAttribute("keyword", pageRequestDTO.getKeyword());
        }

        return "redirect:/board/read";
    }

}
