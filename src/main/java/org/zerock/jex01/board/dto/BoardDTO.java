package org.zerock.jex01.board.dto;

import jdk.vm.ci.meta.Local;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.jex01.board.domain.Board;
import org.zerock.jex01.board.domain.BoardAttach;
import org.zerock.jex01.common.dto.UploadResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    //Controller에서는 이 형식으로 수집한다. -> mapper에서 수집하려면 VO형식으로 바꿔줘야함
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder.Default
    private List<UploadResponseDTO> files = new ArrayList<>();

    public Board getDomain(){
        Board board = Board.builder()
                .bno(bno)
                .title(title)
                .content(content)
                .writer(writer)
                .regDate(regDate)
                .modDate(modDate)
                .build(); //화면에 있는 데이터와 데이터베이스에 있는 구조가 다를 때 필요함

        //파일의 구조 바꿔줘야함
        files.forEach(uploadResponseDTO -> {
            BoardAttach attach = BoardAttach.builder()
                    .fileName(uploadResponseDTO.getFileName())
                    .uuid(uploadResponseDTO.getUuid())
                    .image(uploadResponseDTO.isImage())
                    .path(uploadResponseDTO.getUploadPath())
                    .build();

            board.addAttach(attach);
        });

        return board;
    }
}
