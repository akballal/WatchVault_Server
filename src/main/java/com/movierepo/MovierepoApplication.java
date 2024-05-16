package com.movierepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class MovierepoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovierepoApplication.class, args);
	}

}
