package com.example.demo.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem implements Parcelable {
    private int icon;
    private  int classId;
    private  String name;

    public MenuItem(int icon, int classId, String name) {
        this.icon = icon;
        this.classId = classId;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.icon);
        dest.writeInt(this.classId);
        dest.writeString(this.name);
    }

    public void readFromParcel(Parcel source) {
        this.icon = source.readInt();
        this.classId = source.readInt();
        this.name = source.readString();
    }

    protected MenuItem(Parcel in) {
        this.icon = in.readInt();
        this.classId = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<MenuItem> CREATOR = new Parcelable.Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel source) {
            return new MenuItem(source);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };
}
