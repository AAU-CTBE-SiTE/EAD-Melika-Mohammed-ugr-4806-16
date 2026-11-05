package com.craft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan("com.craft")
public class AppConfig {

    @Bean
    @Order(1)
    public Subject math() {
        Subject subject = new Subject();
        subject.setSubjectName("Maths 101");
        return subject;
    }

    @Bean
    @Order(2)
    public Subject java() {
        Subject subject = new Subject();
        subject.setSubjectName("Java EE");
        return subject;
    }
}