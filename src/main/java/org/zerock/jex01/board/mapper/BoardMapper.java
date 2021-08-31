package org.zerock.jex01.board.mapper;

import org.zerock.jex01.board.domain.Board;

public interface BoardMapper {

    //@insert로 만들지 말기 -> xml로 빠짐
    void insert(Board board);

}
