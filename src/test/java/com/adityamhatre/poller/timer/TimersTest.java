package com.adityamhatre.poller.timer;

import dto.SongDTO;
import dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class TimersTest {
	//	@Autowired
	Timers cut;

	@Test
	void test1() {
		UserDTO _5minUser = UserDTO.builder()
				.objectId("5min_user")
				.frequency(5)
				.build();
		UserDTO _15minUser = UserDTO.builder()
				.objectId("15min_user")
				.frequency(15)
				.build();

		SongDTO _5minSong = SongDTO.builder()
				.shazamedBy(_5minUser)
				.build();

		SongDTO _15minSong = SongDTO.builder()
				.shazamedBy(_15minUser)
				.build();

		new Thread(() -> {
			System.out.println("adding to 5 min");
			cut.addToAppropriateTimer(_5minSong);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("adding to 15 mins");
					cut.addToAppropriateTimer(_15minSong);
				}
			}, 16 * 1000);
		}).start();


		while (true) {
		}

	}


	@Test
	void test2() {
		ArrayList<Integer> integers = new ArrayList<>();
		integers.add(1);
		integers.add(2);
		integers.add(3);
		integers.add(4);
		Iterator<Integer> integerIterator = integers.iterator();
		while (integerIterator.hasNext()) {
			System.out.println(integerIterator.next());
			integerIterator.remove();
		}

		System.out.println("after removing");
		integers.forEach(System.out::println);
	}

}