package com.scmnew;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScmnewApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScmnewApplication.class, args);

		
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("App Started ");
	}

}
