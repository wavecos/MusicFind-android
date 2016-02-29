package com.xiobit.musicfind.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiobit.musicfind.R;
import com.xiobit.musicfind.helper.Constants;
import com.xiobit.musicfind.model.Song;

/**
 * Created by onix on 2/28/16.
 */
public class DetailActivity extends  AppCompatActivity {

    private ImageView albumImageView;
    private TextView trackNameTextView;
    private TextView collectionTextView;


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

    }

    private void configViews() {
        albumImageView = (ImageView) findViewById(R.id.albumImageView);
        trackNameTextView = (TextView) findViewById(R.id.trackNameTextView);
        collectionTextView = (TextView) findViewById(R.id.collectionTextView);
    }

}
