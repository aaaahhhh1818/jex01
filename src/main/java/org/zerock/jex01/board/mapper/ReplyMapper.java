package org.zerock.jex01.board.mapper;

import org.zerock.jex01.board.domain.Reply;
import org.zerock.jex01.board.dto.ReplyDTO;

import java.util.List;

public interface ReplyMapper {

    int insert(Reply reply);

    List<Reply> getListWithBoard(Long bno);

    int delete(Long rno);

    int update(Reply reply);

}
