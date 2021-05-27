package com.example.demo.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class CookBook implements Parcelable {

    private List<Processes> processes;
    private List<Materials> materials;
    private int bookId;
    private String name;
    private String pic;
    private int userId;
    private int peoplenum;
    private String content;
    private int classId;
    private Date createTime;
    public void setProcesses(List<Processes> processes) {
        this.processes = processes;
    }
    public List<Processes> getProcesses() {
        return processes;
    }

    public void setMaterials(List<Materials> materials) {
        this.materials = materials;
    }
    public List<Materials> getMaterials() {
        return materials;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getBookId() {
        return bookId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getPic() {
        return pic;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public void setPeoplenum(int peoplenum) {
        this.peoplenum = peoplenum;
    }
    public int getPeoplenum() {
        return peoplenum;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
    public int getClassId() {
        return classId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public String toString() {
        return "CookBook{" +
                "processes=" + processes +
                ", materials=" + materials +
                ", bookId=" + bookId +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", userId=" + userId +
                ", peoplenum=" + peoplenum +
                ", content='" + content + '\'' +
                ", classId=" + classId +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.processes);
        dest.writeList(this.materials);
        dest.writeInt(this.bookId);
        dest.writeString(this.name);
        dest.writeString(this.pic);
        dest.writeInt(this.userId);
        dest.writeInt(this.peoplenum);
        dest.writeString(this.content);
        dest.writeInt(this.classId);
        dest.writeLong(this.createTime != null ? this.createTime.getTime() : -1);
    }

    public void readFromParcel(Parcel source) {
        this.processes = new ArrayList<Processes>();
        source.readList(this.processes, Processes.class.getClassLoader());
        this.materials = new ArrayList<Materials>();
        source.readList(this.materials, Materials.class.getClassLoader());
        this.bookId = source.readInt();
        this.name = source.readString();
        this.pic = source.readString();
        this.userId = source.readInt();
        this.peoplenum = source.readInt();
        this.content = source.readString();
        this.classId = source.readInt();
        long tmpCreateTime = source.readLong();
        this.createTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
    }

    public CookBook() {
    }

    protected CookBook(Parcel in) {
        this.processes = new ArrayList<Processes>();
        in.readList(this.processes, Processes.class.getClassLoader());
        this.materials = new ArrayList<Materials>();
        in.readList(this.materials, Materials.class.getClassLoader());
        this.bookId = in.readInt();
        this.name = in.readString();
        this.pic = in.readString();
        this.userId = in.readInt();
        this.peoplenum = in.readInt();
        this.content = in.readString();
        this.classId = in.readInt();
        long tmpCreateTime = in.readLong();
        this.createTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
    }

    public static final Parcelable.Creator<CookBook> CREATOR = new Parcelable.Creator<CookBook>() {
        @Override
        public CookBook createFromParcel(Parcel source) {
            return new CookBook(source);
        }

        @Override
        public CookBook[] newArray(int size) {
            return new CookBook[size];
        }
    };
}