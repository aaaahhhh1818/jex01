package org.zerock.jex01.board.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.jex01.board.config.BoardRootConfig;
import org.zerock.jex01.common.config.RootConfig;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {BoardRootConfig.class, RootConfig.class})// 두 개다 로딩해줘야 에러가 안난다
public class TimeMapperTests {

    @Autowired
    TimeMapper timeMapper;//timemapper 주입

    @Test
    public void testGetTime(){
        log.info("--------------------");
        log.info("--------------------");
        log.info(timeMapper.getTime2());
        log.info("--------------------");
        log.info("--------------------");
    }
}
