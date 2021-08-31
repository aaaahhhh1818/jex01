package org.zerock.jex01.board.service;

import org.zerock.jex01.board.dto.BoardDTO;

//등록하는 기능을 추가 -> Controller는 화면에서 입력받는 것을 처리 : DTO를 처리하려고 함. Mapper에는 VO여서 DTO를 VO로 바꿔주는 작업이 필요함
public interface BoardService {

    Long register(BoardDTO boardDTO);
}
