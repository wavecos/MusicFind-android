package com.xiobit.musicfind.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiobit.musicfind.R;
import com.xiobit.musicfind.model.Song;

import java.util.List;

/**
 * Created by onix on 2/27/16.
 */
public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder> {

    private List<Song> songs;

    public SongsAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.row_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Song song = this.songs.get(position);

        holder.trackNameTextView.setText(song.getTrackName());
        holder.collectionNameTextView.setText(song.getCollectionName());

        Picasso.with(holder.itemView.getContext()).load(song.getArtworkUrl100()).into(holder.photoImageView);
    }

    @Override
    public int getItemCount() {
        return this.songs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView photoImageView;
        TextView trackNameTextView;
        TextView collectionNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            photoImageView = (ImageView) itemView.findViewById(R.id.photoImageView);
            trackNameTextView = (TextView) itemView.findViewById(R.id.trackNameTextView);
            collectionNameTextView = (TextView) itemView.findViewById(R.id.collectionNameTextView);
        }
    }

}
