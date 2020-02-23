package com.adityamhatre.poller.kafka;

import com.adityamhatre.poller.timer.Timers;
import dto.SongDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.adityamhatre.poller.kafka.Topics.TopicConstants.ON_USER_FETCHED_ALL_SONGS;
import static com.adityamhatre.poller.kafka.Topics.TopicConstants.ON_USER_FETCHED_NEW_SONGS;

@Component
@Slf4j
public class KafkaConsumer {
	private final Timers timers;

	public KafkaConsumer(Timers timers) {
		this.timers = timers;
	}

	@KafkaListener(topics = ON_USER_FETCHED_ALL_SONGS, groupId = "poller", containerFactory = "kafkaListenerContainerFactory")
	void pollNewSongsToFacade(ConsumerRecord<String, SongDTO> record) {
		log.info("Got new value on \"{}\" channel", ON_USER_FETCHED_ALL_SONGS);
		SongDTO songDTO = record.value();
		timers.addToAppropriateTimer(songDTO);
	}

	@KafkaListener(topics = ON_USER_FETCHED_NEW_SONGS, groupId = "poller", containerFactory = "kafkaListenerContainerFactory")
	void updateAppropriateTimer(ConsumerRecord<String, SongDTO> record) {
		log.info("Got new value on \"{}\" channel", ON_USER_FETCHED_NEW_SONGS);
		SongDTO songDTO = record.value();
		timers.updateAppropriateTimer(songDTO);
	}
}
