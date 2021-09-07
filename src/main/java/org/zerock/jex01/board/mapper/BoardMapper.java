package org.zerock.jex01.board.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.common.dto.PageRequestDTO;

import java.util.List;

public interface BoardMapper {

    //@insert로 만들지 말기 -> xml로 빠짐
    void insert(Board board);

    List<Board> getList(PageRequestDTO pageRequestDTO); //각각의 row 어떻게 처리할지만 지정해주면 됨

    int getCount(PageRequestDTO pageRequestDTO);

    Board select(Long bno);

    int delete(Long bno);

    int update(Board board);

    int updateReplyCnt(@Param("bno") Long bno,@Param("num") int num); //글을 추가할 때나 삭제할 때 카운트 해줘야함 //MyBatis는 파라이터 하나 밖에 못받음

}
