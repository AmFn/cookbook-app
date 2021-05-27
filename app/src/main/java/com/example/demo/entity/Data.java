package com.example.demo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Data implements Parcelable {

            List<CookBook> foodList;
            CookBook foodDetail;
            User userInfo;
            User user;

    @Override
    public String toString() {
        return "Data{" +
                "foodList=" + foodList +
                ", foodDetail=" + foodDetail +
                ", userInfo=" + userInfo +
                ", user=" + user +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CookBook> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<CookBook> foodList) {
        this.foodList = foodList;
    }

    public CookBook getFoodDetail() {
        return foodDetail;
    }

    public void setFoodDetail(CookBook foodDetail) {
        this.foodDetail = foodDetail;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.foodList);
        dest.writeParcelable(this.foodDetail, flags);
        dest.writeParcelable(this.userInfo, flags);
        dest.writeParcelable(this.user, flags);
    }

    public void readFromParcel(Parcel source) {
        this.foodList = source.createTypedArrayList(CookBook.CREATOR);
        this.foodDetail = source.readParcelable(CookBook.class.getClassLoader());
        this.userInfo = source.readParcelable(User.class.getClassLoader());
        this.user = source.readParcelable(User.class.getClassLoader());
    }

    public Data() {
    }

    protected Data(Parcel in) {
        this.foodList = in.createTypedArrayList(CookBook.CREATOR);
        this.foodDetail = in.readParcelable(CookBook.class.getClassLoader());
        this.userInfo = in.readParcelable(User.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
