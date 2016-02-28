package com.xiobit.musicfind.service;

import com.xiobit.musicfind.helper.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by onix on 2/25/16.
 */
public class RestManager {

    private ITunesService iTunesService;

    public ITunesService getiTunesService() {
        if (iTunesService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            iTunesService = retrofit.create(ITunesService.class);
        }

        return iTunesService;
    }



}
