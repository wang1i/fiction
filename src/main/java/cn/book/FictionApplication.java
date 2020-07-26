package cn.book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("cn.book.bus.mapper")
@EnableScheduling
public class FictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FictionApplication.class, args);
    }

}
