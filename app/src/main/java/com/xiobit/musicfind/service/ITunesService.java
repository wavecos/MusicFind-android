package com.xiobit.musicfind.service;


import com.xiobit.musicfind.model.SongWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by onix on 2/25/16.
 */
public interface ITunesService {

    @GET("search?entity=song&limit=20")
    Call<SongWrapper> getAllSongs(@Query("term") String term);

}
