package com.adityamhatre.poller.service;

import dto.SongDTO;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface DB {
	@GET("/api/latestSongsPerUser")
	Call<List<SongDTO>> getLatestSongPerUsers();
}
