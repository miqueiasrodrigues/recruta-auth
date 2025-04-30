package br.com.miqueias.recruta_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class RecrutaAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecrutaAuthApplication.class, args);
	}

}
