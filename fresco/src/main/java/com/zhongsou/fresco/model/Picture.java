package com.zhongsou.fresco.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhongsou.fresco.utils.ConfigConstants;

/**
 * Created by yelong on 2015/11/10.
 */
public class Picture implements Parcelable {
    public static final int XGMN = 1;
    public static final int HRMN = 2;
    public static final int SWMT = 3;
    public static final int MNZP = 4;
    public static final int MNXZ = 5;
    public static final int QCMN = 6;
    public static final int XGCM = 7;
    public static final String URL = "http://www.tngou.net/tnfs/api/list?";

    private int id;
    private int galleryclass;
    private String title;
    private String img;
    private int count;
    private int rcount;
    private int fcount;
    private int size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGalleryclass() {
        return galleryclass;
    }

    public void setGalleryclass(int galleryclass) {
        this.galleryclass = galleryclass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return ConfigConstants.mainUrl + img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.galleryclass);
        dest.writeString(this.title);
        dest.writeString(this.img);
        dest.writeInt(this.count);
        dest.writeInt(this.rcount);
        dest.writeInt(this.fcount);
        dest.writeInt(this.size);
    }

    public Picture() {
    }

    public Picture(int id, int galleryclass, String title, String img, int count, int rcount, int fcount, int size) {
        this.id = id;
        this.galleryclass = galleryclass;
        this.title = title;
        this.img = img;
        this.count = count;
        this.rcount = rcount;
        this.fcount = fcount;
        this.size = size;
    }

    protected Picture(Parcel in) {
        this.id = in.readInt();
        this.galleryclass = in.readInt();
        this.title = in.readString();
        this.img = in.readString();
        this.count = in.readInt();
        this.rcount = in.readInt();
        this.fcount = in.readInt();
        this.size = in.readInt();
    }

    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    /**
     * 获取请求的url
     *
     * @param type
     * @param page
     * @return
     */
    public static String getRequestUrl(int type, int page) {

        switch (type) {
            case XGMN:
                return URL + "id=1&page=" + page;
            case HRMN:
                return URL + "id=2&page=" + page;
            case SWMT:
                return URL + "id=3&page=" + page;
            case MNZP:
                return URL + "id=4&page=" + page;
            case MNXZ:
                return URL + "id=5&page=" + page;
            case QCMN:
                return URL + "id=6&page=" + page;
            case XGCM:
                return URL + "id=7&page=" + page;
            default:
                return "";
        }
    }
}
