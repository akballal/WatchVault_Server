package com.movierepo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Movie Repo", version = "2.0", description = "Movie Repo Microservice"))
public class MovierepoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovierepoApplication.class, args);
	}

}
