package com.adityamhatre.poller.service;

import dto.SongDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class DBService {
	@Value("${database-service:http://database-service:8080}")
	private String databaseServiceURL;

	private DB db;

	@PostConstruct
	private void init() {
		this.db = new Retrofit.Builder()
				.baseUrl(databaseServiceURL)
				.addConverterFactory(JacksonConverterFactory.create())
				.build()
				.create(DB.class);
	}

	public List<SongDTO> getLatestSongsPerEachUser() {
		Call<List<SongDTO>> latestSongPerUser = this.db.getLatestSongPerUsers();
		try {
			Response<List<SongDTO>> response = latestSongPerUser.execute();
			if (response.isSuccessful()) {
				return response.body();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}

