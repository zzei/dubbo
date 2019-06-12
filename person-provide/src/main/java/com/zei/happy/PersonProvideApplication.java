package com.zei.happy;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class PersonProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonProvideApplication.class, args);
	}

}
