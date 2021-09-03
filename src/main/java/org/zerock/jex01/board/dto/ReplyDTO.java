package org.zerock.jex01.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyDTO {

    private Long rno;
    private Long bno;
    private String replyer;
    private String reply;
    private LocalDateTime replyDate;
    private LocalDateTime modDate;

}
