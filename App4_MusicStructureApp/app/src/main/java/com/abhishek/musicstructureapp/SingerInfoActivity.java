package com.abhishek.musicstructureapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class SingerInfoActivity extends AppCompatActivity {
    public static final String ALBUM="album";
    public static final String SINGER="singer";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer_info);
        Intent intent =getIntent();
        String title = intent.getStringExtra(SINGER);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseToolbar);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                Intent intent = new Intent(SingerInfoActivity.this,AlbumInfoActivity.class);
                intent.putExtra(ALBUM, Parcels.wrap(albumList.get(position)));
                startActivity(intent);
            }
        });
        gridView.setAdapter(adapter);
        gridView.addItemDecoration(new SpaceItemDecoration(4));
    }
}
