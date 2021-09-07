package org.zerock.jex01.board.service;

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
@ContextConfiguration( classes = {BoardRootConfig.class, RootConfig.class})
public class TimeServiceTests {

    @Autowired
    TimeService timeService;

    @Test
    public void testAdd() {

        String str = "starry, starry night\n" +
                "Paint your palette blue ang gray\n" +
                "Look out on a summer''s day\n" +
                "With eyes that know the darkness in my soul";

        timeService.addString(str);

    }

}
