package com.abhishek.musicstructureapp;


import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel(Parcel.Serialization.BEAN)
public class Album {
    private String albumName;
    private String singer;
    private int songsCount=0;
    private List<String> songs;
    private int drawableId;
    public Album()
    {
    }
    public Album(String name,String singer,int drawableId)
    {
        this.albumName = name;
        this.singer=singer;
        this.drawableId=drawableId;
        this.songs = new ArrayList<>();
    }
    public void addSongs(String songName)
    {
        songs.add(songName);
        songsCount++;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getSongsCount() {
        return songsCount;
    }

    public List<String> getSongs() {
        return songs;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getSinger() {
        return singer;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSongsCount(int songsCount) {
        this.songsCount = songsCount;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }
}
