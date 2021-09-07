package org.zerock.jex01.board.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TimeService {

    String getNow();

    void addString(String str);
}
