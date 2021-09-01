package org.zerock.jex01.board.mapper;

import org.zerock.jex01.board.domain.Board;

import java.util.List;

public interface BoardMapper {

    //@insert로 만들지 말기 -> xml로 빠짐
    void insert(Board board);

    List<Board> getList(); //각각의 row 어떻게 처리할지만 지정해주면 됨

    Board select(Long bno);

    int delete(Long bno);

    int update(Board board);

}
