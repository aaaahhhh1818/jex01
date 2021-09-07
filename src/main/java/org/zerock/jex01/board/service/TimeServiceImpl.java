package org.zerock.jex01.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.jex01.board.mapper.TimeMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService{ //구현하는 애들은 다 Impl추가

    private final TimeMapper timeMapper;

    @Override
    public String getNow() {

        return timeMapper.getTime2();
    }

    @Override
    public void addString(String str) {
        timeMapper.insertE1(str);
        timeMapper.insertE2(str);
    }
}
