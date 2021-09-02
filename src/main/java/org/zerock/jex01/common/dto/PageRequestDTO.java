package org.zerock.jex01.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default //기본값 주고 선언
    private int page = 1;
    @Builder.Default
    private int size = 10;

    private String type; //검색 조건
    private String keyword; //검색어

    public int getSkip() {

        return (page - 1) * size;
    }

    public String[] getArr() {
        return type == null? new String[]{}: type.split("");
    }
    //빈 배열 주는 이유 : sql에서 에러나기 때문

}
