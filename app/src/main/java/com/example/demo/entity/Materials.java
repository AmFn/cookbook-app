package com.example.demo.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Materials implements Parcelable {

    private int materialId;
    private String name;
    private int bookId;
    private String amount;
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
    public int getMaterialId() {
        return materialId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getBookId() {
        return bookId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getAmount() {
        return amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.materialId);
        dest.writeString(this.name);
        dest.writeInt(this.bookId);
        dest.writeString(this.amount);
    }

    public void readFromParcel(Parcel source) {
        this.materialId = source.readInt();
        this.name = source.readString();
        this.bookId = source.readInt();
        this.amount = source.readString();
    }

    public Materials() {
    }

    protected Materials(Parcel in) {
        this.materialId = in.readInt();
        this.name = in.readString();
        this.bookId = in.readInt();
        this.amount = in.readString();
    }

    public static final Parcelable.Creator<Materials> CREATOR = new Parcelable.Creator<Materials>() {
        @Override
        public Materials createFromParcel(Parcel source) {
            return new Materials(source);
        }

        @Override
        public Materials[] newArray(int size) {
            return new Materials[size];
        }
    };

    @Override
    public String toString() {
        return "Materials{" +
                "materialId=" + materialId +
                ", name='" + name + '\'' +
                ", bookId=" + bookId +
                ", amount='" + amount + '\'' +
                '}';
    }
}