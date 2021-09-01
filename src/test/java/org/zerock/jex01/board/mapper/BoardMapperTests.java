package org.zerock.jex01.board.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.common.config.RootConfig;

import java.util.stream.IntStream;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {BoardRootConfig.class, RootConfig.class})
public class BoardMapperTests {

    @Autowired
    BoardMapper boardMapper;

    @Test
    public void testDummies(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("user" + (i % 10))
                    .build();

            boardMapper.insert(board);
        });
    }

    @Test
    public void testList() {

        boardMapper.getList().forEach(board -> log.info(board));

    }

    @Test
    public void testSelect() {

        log.info(boardMapper.select(229L));

    }

    @Test
    public void testDelete() {

        long bno = 213L;

        log.info("delete..........");
        log.info(boardMapper.delete(bno));

    }

    @Test
    public void testUpdate() {

        //가짜 값을 넣은 Board
        Board board = Board.builder()
                .bno(228L)
                .title("수정된 제목")
                .content("수정된 내용..228")
                .build();

        log.info(boardMapper.update(board));

    }

}
