package org.zerock.jex01.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.jex01.board.dto.ReplyDTO;
import org.zerock.jex01.board.mapper.BoardMapper;
import org.zerock.jex01.board.mapper.ReplyMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper; //생성자를 통해서 주입받아야 하기 때문에 @RequiredArgsConstructor 걸어줘야함
    private final BoardMapper boardMapper;

    @Override
    public int add(ReplyDTO replyDTO) {
        int count = replyMapper.insert(dtoToEntity(replyDTO));
        boardMapper.updateReplyCnt(replyDTO.getBno(), 1);

        return count;
    }

    @Override
    public List<ReplyDTO> getRepliesWithBno(Long bno) {

        return replyMapper.getListWithBoard(bno).stream().map(reply -> entityToDTO(reply)).collect(Collectors.toList());
    }

    @Override
    public int Remove(Long rno) {
        return replyMapper.delete(rno);
    }

    @Override
    public int modify(ReplyDTO replyDTO) {
        return replyMapper.update(dtoToEntity(replyDTO));
    }

}
