package org.zerock.jex01.board.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TimeMapper {

    @Select("select now()")
    String getTime();

    String getTime2();

    @Insert("insert into tbl_e1 (col1) values (#{str})")
    void insertE1(String str);

    @Insert("insert into tbl_e2 (col2) values (#{str})")
    void insertE2(String str);
}
