package com.example.practicamoviles_1tr.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MapPoint implements Parcelable {

    private String title;

    private Location location;

    //booleano para controlar si se ha guardado en favoritos
    private boolean isFav;

    public MapPoint(String title, Location location) {
        this.title = title;
        this.location = location;
    }
    public MapPoint(String title, Location location, boolean isFav) {
        this.title = title;
        this.location = location;
        this.isFav=isFav;
    }

    public MapPoint(){}

    protected MapPoint(Parcel in) {
        title = in.readString();
    }

    public static final Creator<MapPoint> CREATOR = new Creator<MapPoint>() {
        @Override
        public MapPoint createFromParcel(Parcel in) {
            return new MapPoint(in);
        }

        @Override
        public MapPoint[] newArray(int size) {
            return new MapPoint[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeParcelable(location, flags);

    }
}
