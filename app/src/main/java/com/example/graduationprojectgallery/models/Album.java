package com.example.graduationprojectgallery.models;

import android.net.Uri;

public class Album {


    public Album(String name, Uri thumbnail_path) {

        this.name = name;
        this.thumbnail_path = thumbnail_path;
    }


    private String name;

    public Uri getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(Uri thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    private Uri thumbnail_path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
