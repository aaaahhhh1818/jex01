package org.zerock.jex01.board.service;

import org.zerock.jex01.board.domain.Reply;
import org.zerock.jex01.board.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    int add(ReplyDTO replyDTO);

    List<ReplyDTO> getRepliesWithBno(Long bno);

    int Remove(Long rno);

    int modify(ReplyDTO replyDTO);

    default Reply dtoToEntity(ReplyDTO dto) {
        Reply reply = Reply.builder()
                .rno(dto.getRno())
                .bno(dto.getBno())
                .reply(dto.getReply())
                .replyer(dto.getReplyer())
                .replyDate(dto.getReplyDate())
                .modDate(dto.getModDate())
                .build();
        return reply;
    }

    //default 쓰면 인터페이스 몸체 만들 수 있음
    default ReplyDTO entityToDTO(Reply reply) {

        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .bno(reply.getBno())
                .reply(reply.getReply())
                .replyer(reply.getReplyer())
                .replyDate(reply.getReplyDate())
                .modDate(reply.getModDate())
                .build();

        return replyDTO;
    }

}
