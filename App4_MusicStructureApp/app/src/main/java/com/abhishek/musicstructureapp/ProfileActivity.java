package com.abhishek.musicstructureapp;

import android.content.Intent;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    public static final String ALBUM="album";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Profile");
        RecyclerView gridView = (RecyclerView) findViewById(R.id.recyclerGridView);
        final List<Album> albumList = new ArrayList<>();
        albumList.add(new Album("Hybrid Theory","Linkin Park",R.drawable.hybrid_theory));
        albumList.add(new Album("Hybrid Theory","Linkin Park",R.drawable.hybrid_theory));
        albumList.add(new Album("Hybrid Theory","Linkin Park",R.drawable.hybrid_theory));
        albumList.add(new Album("Hybrid Theory","Linkin Park",R.drawable.hybrid_theory));
        albumList.add(new Album("Hybrid Theory","Linkin Park",R.drawable.hybrid_theory));
        albumList.add(new Album("Hybrid Theory","Linkin Park",R.drawable.hybrid_theory));
        albumList.add(new Album("Hybrid Theory","Linkin Park",R.drawable.hybrid_theory));
        albumList.add(new Album("Hybrid Theory","Linkin Park",R.drawable.hybrid_theory));
        gridView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        gridView.setLayoutManager(layoutManager);
        GridAdapter adapter = new GridAdapter(this,albumList);
        adapter.setOnItemClickListener(new GridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(ProfileActivity.this,AlbumInfoActivity.class);
                intent.putExtra(ALBUM, Parcels.wrap(albumList.get(position)));
                startActivity(intent);
            }
        });
        gridView.setAdapter(adapter);
        gridView.addItemDecoration(new SpaceItemDecoration(4));

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,AddSongActivity.class);
                startActivity(intent);
            }
        });
    }
}
