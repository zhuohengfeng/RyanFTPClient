package com.ryan.ftp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FileItemBean implements Parcelable {
    private String name;
    private String path;
    private int type;
    private int width;
    private int height;

    public FileItemBean() {
    }

    @Override
    public String toString() {
        return "FileItemBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type=" + type +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public String getName() {
        return name;
    }

    public FileItemBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public FileItemBean setPath(String path) {
        this.path = path;
        return this;
    }

    public int getType() {
        return type;
    }

    public FileItemBean setType(int type) {
        this.type = type;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public FileItemBean setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public FileItemBean setHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeInt(this.type);
    }

    protected FileItemBean(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<FileItemBean> CREATOR = new Parcelable.Creator<FileItemBean>() {
        @Override
        public FileItemBean createFromParcel(Parcel source) {
            return new FileItemBean(source);
        }

        @Override
        public FileItemBean[] newArray(int size) {
            return new FileItemBean[size];
        }
    };



}
