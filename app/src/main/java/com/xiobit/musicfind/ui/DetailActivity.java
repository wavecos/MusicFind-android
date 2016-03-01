package com.xiobit.musicfind.ui;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiobit.musicfind.R;
import com.xiobit.musicfind.helper.Constants;
import com.xiobit.musicfind.model.Song;

import java.io.IOException;

/**
 * Created by onix on 2/28/16.
 */
public class DetailActivity extends  AppCompatActivity implements MediaPlayer.OnPreparedListener {

    public static final String TAG = DetailActivity.class.getSimpleName();

    private ImageView albumImageView;
    private TextView trackNameTextView;
    private TextView collectionTextView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        Song song = (Song) intent.getSerializableExtra(Constants.REFERENCE.SONG);

        configViews();

        trackNameTextView.setText(song.getTrackName());
        collectionTextView.setText(song.getCollectionName());

        Picasso.with(this).load(song.getArtworkUrl100()).into(albumImageView);

        // Setup MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(song.getPreviewUrl());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getLocalizedMessage());
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    private void configViews() {
        albumImageView = (ImageView) findViewById(R.id.albumImageView);
        trackNameTextView = (TextView) findViewById(R.id.trackNameTextView);
        collectionTextView = (TextView) findViewById(R.id.collectionTextView);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
