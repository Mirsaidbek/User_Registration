package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAsync
@EnableWebSecurity
public class Main {

//     @Bean
//    public JavaMailSender javaMailSender(){
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        return javaMailSender;
//    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}