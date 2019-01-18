package com.ryan.ftp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FileItemBean implements Parcelable {
    private int tag;

    private String name;
    private String path;
    private int type;
    private int width;
    private int height;

    private String t_name;
    private String t_path;
    private int t_type;
    private int t_width;
    private int t_height;

    public int getTag() {
        return tag;
    }

    public FileItemBean setTag(int tag) {
        this.tag = tag;
        return this;
    }

    public String getTname() {
        return t_name;
    }

    public FileItemBean setTname(String t_name) {
        this.t_name = t_name;
        return this;
    }

    public String getTpath() {
        return t_path;
    }

    public FileItemBean setTpath(String t_path) {
        this.t_path = t_path;
        return this;
    }

    public int getTtype() {
        return t_type;
    }

    public FileItemBean setTtype(int t_type) {
        this.t_type = t_type;
        return this;
    }

    public int getTwidth() {
        return t_width;
    }

    public FileItemBean setTwidth(int t_width) {
        this.t_width = t_width;
        return this;
    }

    public int getTheight() {
        return t_height;
    }

    public FileItemBean setTheight(int t_height) {
        this.t_height = t_height;
        return this;
    }

    public FileItemBean() {
    }

    @Override
    public String toString() {
        return "FileItemBean{" +
                "tag=" + tag +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type=" + type +
                ", width=" + width +
                ", height=" + height +
                ", t_name='" + t_name + '\'' +
                ", t_path='" + t_path + '\'' +
                ", t_type=" + t_type +
                ", t_width=" + t_width +
                ", t_height=" + t_height +
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

        dest.writeString(this.t_name);
        dest.writeString(this.t_path);
        dest.writeInt(this.t_width);
        dest.writeInt(this.t_height);
        dest.writeInt(this.t_type);

        dest.writeInt(this.tag);
    }

    protected FileItemBean(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.type = in.readInt();

        this.t_name = in.readString();
        this.t_path = in.readString();
        this.t_width = in.readInt();
        this.t_height = in.readInt();
        this.t_type = in.readInt();

        this.tag = in.readInt();
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
