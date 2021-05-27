package com.example.demo.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class Processes implements Parcelable {

        private int processId;
        private int bookId;
        private int sort;
        private String pic;
        private String content;
        public void setProcessId(int processId) {
            this.processId = processId;
        }
        public int getProcessId() {
            return processId;
        }

        public void setBookId(int bookId) {
            this.bookId = bookId;
        }
        public int getBookId() {
            return bookId;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
        public int getSort() {
            return sort;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
        public String getPic() {
            return pic;
        }

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.processId);
        dest.writeInt(this.bookId);
        dest.writeInt(this.sort);
        dest.writeString(this.pic);
        dest.writeString(this.content);
    }

    public void readFromParcel(Parcel source) {
        this.processId = source.readInt();
        this.bookId = source.readInt();
        this.sort = source.readInt();
        this.pic = source.readString();
        this.content = source.readString();
    }

    public Processes() {
    }

    protected Processes(Parcel in) {
        this.processId = in.readInt();
        this.bookId = in.readInt();
        this.sort = in.readInt();
        this.pic = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<Processes> CREATOR = new Parcelable.Creator<Processes>() {
        @Override
        public Processes createFromParcel(Parcel source) {
            return new Processes(source);
        }

        @Override
        public Processes[] newArray(int size) {
            return new Processes[size];
        }
    };
}