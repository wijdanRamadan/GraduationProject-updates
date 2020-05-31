package com.example.graduationprojectgallery.models;

import android.net.Uri;

public class person {

    String name ;
    Uri thumbnail ;



    public person(String name, Uri thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Uri thumbnail) {
        this.thumbnail = thumbnail;
    }



}
