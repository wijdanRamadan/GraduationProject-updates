package com.example.graduationprojectgallery.models;

import android.widget.Spinner;

import androidx.room.Entity;

import java.io.Serializable;


@Entity
public class PhotoModel implements Serializable {

    private String path;
    private String date;
    private String title;
    private String size;
    private String latitude;
    private String longitude;
    private boolean isSelect;


    public PhotoModel(String path, String date, String title, String size, String latitude, String longitude) {
        this.path = path;
        this.date = date;
        this.title = title;
        this.size = size;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public PhotoModel() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String Latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String Longitude) {
        this.longitude = longitude;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }
}