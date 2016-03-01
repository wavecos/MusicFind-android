package com.xiobit.musicfind.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.xiobit.musicfind.R;
import com.xiobit.musicfind.adapter.SongsAdapter;
import com.xiobit.musicfind.helper.Constants;
import com.xiobit.musicfind.model.Song;
import com.xiobit.musicfind.model.SongWrapper;
import com.xiobit.musicfind.service.RestManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SongsAdapter.SongClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private SongsAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    private void configViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.pbLoading);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SongsAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    // Call Backend Methods
    private void getSongs(String artist) {
        // Test Call from Backend

        progressBar.setVisibility(ProgressBar.VISIBLE);

        RestManager restManager = new RestManager();
        Call<SongWrapper> listCall = restManager.getiTunesService().getAllSongs(artist);

        listCall.enqueue(new Callback<SongWrapper>() {
            @Override
            public void onResponse(Call<SongWrapper> call, Response<SongWrapper> response) {
                if (response.isSuccess()) {
                    SongWrapper body = response.body();

                    for (Song song : body.getResults()) {
                        Log.d(TAG, "salida: " + song.getTrackName());
                    }
                    adapter.addSongs(response.body().getResults());
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }

                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(Call<SongWrapper> call, Throwable t) {
                Log.e(TAG, "error : " + t.getLocalizedMessage());
                Snackbar.make(recyclerView, t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, query);
        getSongs(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG, "onClick : " + position);
        Song selectedSong = adapter.getSelectedSong(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        intent.putExtra(Constants.REFERENCE.SONG, selectedSong);
        startActivity(intent);
    }
}
