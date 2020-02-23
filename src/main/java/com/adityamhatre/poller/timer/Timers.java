package com.adityamhatre.poller.timer;

import com.adityamhatre.poller.kafka.KafkaProducer;
import dto.SongDTO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class Timers {
	private final KafkaProducer kafkaProducer;

	private List<Integer> defaultTimes = Arrays.asList(1, 5, 10, 15, 20);
	private Map<Integer, TimerTask> timerTasksMapped = new HashMap<>();

	//SongDTO has the user information in it
	//The "poller" system only gets events from ON_USER_FETCHED_ALL_SONGS and ON_USER_FETCHED_NEW_SONGS topic which sends me a SongDTO object
	private Map<Integer, HashSet<SongDTO>> usersMapped = new HashMap<>();

	public Timers(KafkaProducer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

	@PostConstruct
	void startTimers() {
		createTimers();

		timerTasksMapped.keySet().forEach(interval -> new Timer().scheduleAtFixedRate(timerTasksMapped.get(interval), 0, interval * 1000 * 60));

	}

	private void createTimers() {
		defaultTimes.forEach(interval -> timerTasksMapped.put(interval, getTimerTaskForInterval(interval)));
	}

	private TimerTask getTimerTaskForInterval(Integer interval) {
		return new TimerTask() {
			@Override
			public void run() {
				HashSet<SongDTO> myUsers = usersMapped.get(interval);
				if (myUsers == null) {
					myUsers = new HashSet<>();
				}

				// Produce a poll event and remove this SongDTO from timer lists and add it back again after this poll has fetched all the new songs. Search for !SMART! in this file
				Iterator<SongDTO> songDTOIterator = myUsers.iterator();
				while (songDTOIterator.hasNext()) {
					kafkaProducer.producePollSongsPerUserAfterThisSong(songDTOIterator.next());
					songDTOIterator.remove();
				}
			}
		};
	}


	public void addToAppropriateTimer(SongDTO songDTO) {
		if (usersMapped.containsKey(songDTO.getShazamedBy().getFrequency())) {
			usersMapped.get(songDTO.getShazamedBy().getFrequency()).add(songDTO);
		} else {
			HashSet<SongDTO> singleUserDTO = new HashSet<>();//need this because we want a mutable list so that we can add users later
			singleUserDTO.add(songDTO);
			usersMapped.put(songDTO.getShazamedBy().getFrequency(), singleUserDTO);
		}
	}

	public void updateAppropriateTimer(SongDTO songDTO) {
		/*
		 !SMART!
		 Yoo!, What's up!?
		 Lol yes, adding the songDTO back here since all new songs have been fetched
		*/
		int frequency = songDTO.getShazamedBy().getFrequency();
		if (usersMapped.containsKey(frequency))
			usersMapped.get(frequency).add(songDTO);
		else {
			HashSet<SongDTO> singleton = new HashSet<>();
			singleton.add(songDTO);
			usersMapped.put(frequency, singleton);
		}
	}
}
