package com.crazyatom.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@EnableJpaRepositories(basePackages = "com.crazyatom.domain.repository")
@EntityScan(basePackages = "com.crazyatom.domain.entity")
@ComponentScan(basePackages = {"com.crazyatom.domain", "com.crazyatom.web"})
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

}
