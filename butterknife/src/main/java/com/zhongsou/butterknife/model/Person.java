package com.zhongsou.butterknife.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yelong on 2015/10/12.
 * 首先进行GsonFormat 然后进行 Parcelable
 */
public class Person implements Parcelable {


    /**
     * name : 王五
     * gender : man
     * age : 15
     * height : 140cm
     */

    private String name;
    private String gender;
    private int age;
    private String height;

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getHeight() {
        return height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.gender);
        dest.writeInt(this.age);
        dest.writeString(this.height);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.gender = in.readString();
        this.age = in.readInt();
        this.height = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
