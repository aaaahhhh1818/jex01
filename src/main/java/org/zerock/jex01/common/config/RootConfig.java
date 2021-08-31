package org.zerock.jex01.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;

//내부적인 부분 : Component / 외부적인 부분 : Configuration
@Configuration //걸자마자 옆에 bean표시가 생김, 자바소스를 빈으로 만들어주는 태그
public class RootConfig {

    @Bean //mybatis 연결
    public SqlSessionFactory sqlSessionFactory() throws Exception{ // mybatis세팅
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }// p.92참고해서 테스트진행

    @Bean //db연결
    //(destroyMethod = "close") : 커넥션 풀을 종료하는 것으로 안전하게 하려면 하는게 좋음
    //싱글톤이라 한 번밖에 로딩안함. (예전에 쓰던 enum같은 것)
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig(); // hikariCP 사용
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/springdb");
        config.setUsername("springuser");
        config.setPassword("springuser");

        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource;
    }

    // 소스코드가 없어서 직접사용을 못하고 @Bean을 이용해서 객체를 생성 / 예전에 xml로 설정했던 것을 메서드로 만들고 @Bean을 걸어서 객체로 생성해줌
    @Bean(name = "names")
    public ArrayList<String> names(){

        ArrayList<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("BBB");
        list.add("CCC");
        return list;

    }
}
