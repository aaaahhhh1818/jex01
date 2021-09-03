package org.zerock.jex01.board.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.jex01.board.dto.ReplyDTO;

@Log4j2
@RestController
@RequestMapping("/replies")
public class ReplyController {

    @GetMapping("")
    public String[] doA() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new String[]{"AAA", "BBB", "CCC"}; //문자열 반환
    }

    @PostMapping("")
    public Long add(@RequestBody ReplyDTO replyDTO) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } //동긴지 비동긴치 확인하려고. 비동기인거 티 안남 ㅋㅋ

        log.info("========================");
        log.info(replyDTO);

        return 1L;
    }

    @DeleteMapping("/{rno}") //rno를 호출하게 설계
    public String remove(@PathVariable(name = "rno") Long rno) { //어노테이션으로 만든 애를 맵핑해주겠다
        log.info("----------------reply remove------------------");

        log.info("rno: " + rno);

        return "success";
    }

    @PutMapping("/{rno}")
    public String modify(@PathVariable(name="rno") Long rno,
                         @RequestBody ReplyDTO replyDTO) {

        log.info("---------------reply modify---------------" + rno);
        log.info(replyDTO);

        return "success";
    }

}
