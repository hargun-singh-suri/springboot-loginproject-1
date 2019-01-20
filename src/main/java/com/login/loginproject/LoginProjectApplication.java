package com.login.loginproject;

import com.login.loginproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginProjectApplication.class, args);

        System.out.println("Hello Spring Boot");

    }

}

