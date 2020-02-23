package com.adityamhatre.poller;

import com.adityamhatre.poller.kafka.KafkaProducer;
import com.adityamhatre.poller.service.DBService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class PollerApplication implements ApplicationRunner {

	private final DBService dbService;
	private final KafkaProducer kafkaProducer;

	public PollerApplication(DBService dbService, KafkaProducer kafkaProducer) {
		this.dbService = dbService;
		this.kafkaProducer = kafkaProducer;
	}

	public static void main(String[] args) {
		SpringApplication.run(PollerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		dbService.getLatestSongsPerEachUser().forEach(kafkaProducer::producePollSongsPerUserAfterThisSong);

	}
}
