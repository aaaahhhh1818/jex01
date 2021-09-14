package org.zerock.jex01.board.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration //자바코드로 bean생성
@MapperScan(basePackages = "org.zerock.jex01.board.mapper")
@ComponentScan(basePackages = "org.zerock.jex01.board.service")
@Import(BoardAOPConfig.class) //로딩될 때 AOP설정된 클래스 불러옴
public class BoardRootConfig{
}