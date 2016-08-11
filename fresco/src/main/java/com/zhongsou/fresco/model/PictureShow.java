package com.zhongsou.fresco.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhongsou.fresco.utils.ConfigConstants;

/**
 * Created by yelong on 2015/11/10.
 */
public class PictureShow implements Parcelable {
    public static final String URL = "http://www.tngou.net/tnfs/api/show?id=";

    private int id;
    private int gallery;
    private String src;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGallery() {
        return gallery;
    }

    public void setGallery(int gallery) {
        this.gallery = gallery;
    }

    public String getSrc() {
        return ConfigConstants.mainUrl + src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.gallery);
        dest.writeString(this.src);
    }

    public PictureShow() {
    }

    public PictureShow(int id, int gallery, String src) {
        this.id = id;
        this.gallery = gallery;
        this.src = src;
    }

    protected PictureShow(Parcel in) {
        this.id = in.readInt();
        this.gallery = in.readInt();
        this.src = in.readString();
    }

    public static final Parcelable.Creator<PictureShow> CREATOR = new Parcelable.Creator<PictureShow>() {
        public PictureShow createFromParcel(Parcel source) {
            return new PictureShow(source);
        }

        public PictureShow[] newArray(int size) {
            return new PictureShow[size];
        }
    };
}
