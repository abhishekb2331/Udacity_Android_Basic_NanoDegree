package com.abhishek.musicstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.parceler.Parcels;

public class AlbumInfoActivity extends AppCompatActivity {
    public static final String ALBUM_INFO="Album Info";
    public static final String SINGER="singer";
    public static final String ALBUM="album";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_info);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(ALBUM_INFO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final Album album = (Album) Parcels.unwrap(intent.getParcelableExtra(ALBUM));
        RecyclerView gridView = (RecyclerView) findViewById(R.id.recyclerGridView);
        gridView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        gridView.setLayoutManager(layoutManager);
        AlbumAdapter adapter = new AlbumAdapter(this,album.getDrawableId(),album);
        adapter.setOnItemClickListener(new AlbumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(AlbumInfoActivity.this,SingerInfoActivity.class);
                intent.putExtra(SINGER, album.getSinger());
                startActivity(intent);
            }
        });
        gridView.setAdapter(adapter);

        album.addSongs("One Step Closer");
        album.addSongs("Crawling");
        album.addSongs("Papercut");
        album.addSongs("In the End");
        album.addSongs("With You");
        album.addSongs("Points of Authority");
        album.addSongs("Runaway");
        album.addSongs("By Myself");
        album.addSongs("A Place for My Head");
        album.addSongs("Forgotten");
        album.addSongs("Cure for the Itch");
        album.addSongs("Pushing Me Away");
        ListView listView = (ListView) findViewById(R.id.song_list);
        listView.setAdapter(new SongAdapter(this,album.getSongs()));
    }
}
