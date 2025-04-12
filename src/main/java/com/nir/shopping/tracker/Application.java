package com.nir.shopping.tracker;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		String salt = BCrypt.gensalt(12);
		String hashed = BCrypt.hashpw("apple@123", salt);
		log.info("Hashed password: {}\nSalt: {}", hashed, salt);
	}

}
