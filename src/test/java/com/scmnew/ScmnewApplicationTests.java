package com.scmnew;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scmnew.services.EmailService;

@SpringBootTest
class ScmnewApplicationTests {

	@Test
	void
	 contextLoads() {
	}

	@Autowired
	private EmailService service;

    @Test
	void setEmailTest(){

		service.sendEmail("tejaskakade2424@gmail.com", "just Managing emails", "this is scm project working on email service");

	}


}
