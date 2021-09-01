package org.zerock.jex01.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.board.dto.BoardDTO;
import org.zerock.jex01.board.mapper.BoardMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper; // 자동으로 주입해줌

    @Override
    public Long register(BoardDTO boardDTO) {

        Board board = boardDTO.getDomain(); //DTO를 VO로 변경

        boardMapper.insert(board);

        return board.getBno();
    }

    @Override
    public List<BoardDTO> getDTOList() {

        return boardMapper.getList().stream().map(board -> board.getDTO()).collect(Collectors.toList());
        //BoardMapper에서 가져온 목록을 한번에 바꿔줌

    }

    @Override
    public BoardDTO read(Long bno) {
        Board board = boardMapper.select(bno);

        if(board != null) {
            return board.getDTO();
        }
        return null;

        //return board != null? board.getDTO(): null; //삼항연산자로도 표현가능

    }

    @Override
    public boolean remove(Long bno) {
        return boardMapper.delete(bno) > 0; //이항연산자 사용해서 1건이상 삭제되면 true로 나옴
    }

    @Override
    public boolean modify(BoardDTO boardDTO) {
        return boardMapper.update(boardDTO.getDomain()) > 0;
    }
}
