package com.adityamhatre.poller.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DBServiceTest {

	@Autowired
	DBService dbService;

	@Test
	void checkDbService() {
		dbService.getLatestSongsPerEachUser().forEach(System.out::println);
	}

}