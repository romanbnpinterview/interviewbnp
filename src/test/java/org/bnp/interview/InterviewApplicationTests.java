package org.bnp.interview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.support.GenericWebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InterviewApplicationTests {

	@Autowired
	GenericWebApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext.isRunning()).isTrue();
	}

}
