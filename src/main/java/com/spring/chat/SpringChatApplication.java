package com.spring.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringChatApplication.class, args);
	}

}
