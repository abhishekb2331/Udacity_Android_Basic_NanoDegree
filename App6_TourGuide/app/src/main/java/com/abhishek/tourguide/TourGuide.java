package com.abhishek.tourguide;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.abhishek.tourguide.model.TourGuideItem;

public class TourGuide extends AppCompatActivity implements ShowMoreFragment.ShowMoreInterface{

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_guide);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.nvView);
        menu= mNavigationView.getMenu();
        mActionBarDrawerToggle =new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        final FragmentManager fm = getSupportFragmentManager();

      //  fm.removeOnBackStackChangedListener();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
        {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = fm.findFragmentById(R.id.content);
                if(fragment instanceof HotelFragment)
                {
                    MenuItem item= menu.findItem(R.id.Third);
                    item.setChecked(true);
                    setTitle(item.getTitle());
                }
                else if(fragment instanceof LocationFragment)
                {
                    MenuItem item= menu.findItem(R.id.First);
                    item.setChecked(true);
                    setTitle(item.getTitle());
                }
                else if(fragment instanceof MuseumFragment)
                {
                    MenuItem item= menu.findItem(R.id.Second);
                    item.setChecked(true);
                    setTitle(item.getTitle());
                }
                else if(fragment instanceof MallFragment)
                {
                    MenuItem item= menu.findItem(R.id.Fourth);
                    item.setChecked(true);
                    setTitle(item.getTitle());
                }
            }
        });
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.content,new LocationFragment());
        ft.commit();

        MenuItem item= menu.findItem(R.id.First);
        item.setChecked(true);
        setTitle(item.getTitle());
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                setTitle(item.getTitle());
                mDrawerLayout.closeDrawers();
                FragmentTransaction ft;
                switch(item.getItemId())
                {
                    case R.id.First:
                        ft = fm.beginTransaction();
                        ft.replace(R.id.content,new LocationFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        return true;
                    case R.id.Second:
                        ft = fm.beginTransaction();
                        ft.replace(R.id.content,new MuseumFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        return true;
                    case R.id.Third:
                        ft = fm.beginTransaction();
                        ft.replace(R.id.content,new HotelFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        return true;
                    case R.id.Fourth:
                        ft = fm.beginTransaction();
                        ft.replace(R.id.content,new MallFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ShowMore(TourGuideItem item) {
        FragmentManager fm = getSupportFragmentManager();
        ShowMoreFragment showMoreFragment = ShowMoreFragment.instance(item);
        showMoreFragment.show(fm,"SHOW MORE");
    }
}
