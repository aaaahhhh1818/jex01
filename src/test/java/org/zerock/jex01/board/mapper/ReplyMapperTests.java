package org.zerock.jex01.board.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.board.domain.Reply;
import org.zerock.jex01.common.config.RootConfig;

import java.util.stream.IntStream;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {BoardRootConfig.class, RootConfig.class})
public class ReplyMapperTests {

    @Autowired(required = false) //spring이 로딩할 때 얘를 체크하지 않음
    ReplyMapper replyMapper;

    @Test
    public void insertDummies() {

        long[] arr = {230L, 229L, 228L, 227L, 226L}; //bno //임의로 게시판 가져오기

        //램덤으로 게시판 따야함
        IntStream.rangeClosed(1, 100).forEach(num -> { //IntStream -> 그냥 루프

            long bno = arr[((int)(Math.random() * 1000)) % 5 ]; //Math.random 0 - 0.999 까지 나옴 5로 나누면 0으로 떨어짐???

            Reply reply = Reply.builder()
                    .bno(bno)
                    .reply("댓글...." + num)
                    .replyer("user" + (num % 10))
                    .build();

            replyMapper.insert(reply);

        });

    }

    @Test
    public void testList() {
        Long bno = 230L;

        replyMapper.getListWithBoard(bno).forEach(reply -> log.info(reply));
    }

}
