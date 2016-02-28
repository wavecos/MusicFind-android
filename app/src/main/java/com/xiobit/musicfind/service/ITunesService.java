package com.xiobit.musicfind.service;


import com.xiobit.musicfind.model.SongWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by onix on 2/25/16.
 */
public interface ITunesService {

    @GET("search?term=beatles&entity=song&limit=20")
    Call<SongWrapper> getAllSongs();

}
