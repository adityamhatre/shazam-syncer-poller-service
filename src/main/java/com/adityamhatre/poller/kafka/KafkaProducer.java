package com.adityamhatre.poller.kafka;

import dto.SongDTO;
import dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.adityamhatre.poller.kafka.Topics.TopicConstants.ON_POLL_TIMER_TICK;

@Slf4j
@Component
public class KafkaProducer {
	private final
	KafkaTemplate<String, SongDTO> pollSongsProducer;

	public KafkaProducer(KafkaTemplate<String, SongDTO> pollSongsProducer) {
		this.pollSongsProducer = pollSongsProducer;
	}

	public void producePollSongsPerUserAfterThisSong(SongDTO song) {
		log.info("Adding new data to \"{}\" channel", ON_POLL_TIMER_TICK);
		this.pollSongsProducer.send(ON_POLL_TIMER_TICK, song);
	}

}
