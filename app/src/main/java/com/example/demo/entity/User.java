package com.example.demo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.demo.entity.CookBook;

import java.util.ArrayList;
import java.util.List;


public class User implements Parcelable {

    private int userId;
    private String nickName;
    private String phome;
    private String avatar;
    private String password;
    private String description;
    private int fansCount;
    private int followCount;
    private List<CookBook> myCookBook;
    private List<User> myFans;
    private List<User> myFollows;
    private List<CookBook> myCollect;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhome() {
        return phome;
    }

    public void setPhome(String phome) {
        this.phome = phome;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public List<CookBook> getMyCookBook() {
        return myCookBook;
    }

    public void setMyCookBook(List<CookBook> myCookBook) {
        this.myCookBook = myCookBook;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", phome='" + phome + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", fansCount=" + fansCount +
                ", followCount=" + followCount +
                ", myCookBook=" + myCookBook +
                ", myFans=" + myFans +
                ", myFollows=" + myFollows +
                ", myCollect=" + myCollect +
                '}';
    }

    public List<User> getMyFans() {
        return myFans;
    }

    public void setMyFans(List<User> myFans) {
        this.myFans = myFans;
    }

    public List<User> getMyFollows() {
        return myFollows;
    }

    public void setMyFollows(List<User> myFollows) {
        this.myFollows = myFollows;
    }

    public List<CookBook> getMyCollect() {
        return myCollect;
    }

    public void setMyCollect(List<CookBook> myCollect) {
        this.myCollect = myCollect;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.nickName);
        dest.writeString(this.phome);
        dest.writeString(this.avatar);
        dest.writeString(this.password);
        dest.writeString(this.description);
        dest.writeInt(this.fansCount);
        dest.writeInt(this.followCount);
        dest.writeTypedList(this.myCookBook);
        dest.writeTypedList(this.myFans);
        dest.writeTypedList(this.myFollows);
        dest.writeTypedList(this.myCollect);
    }

    public void readFromParcel(Parcel source) {
        this.userId = source.readInt();
        this.nickName = source.readString();
        this.phome = source.readString();
        this.avatar = source.readString();
        this.password = source.readString();
        this.description = source.readString();
        this.fansCount = source.readInt();
        this.followCount = source.readInt();
        this.myCookBook = source.createTypedArrayList(CookBook.CREATOR);
        this.myFans = source.createTypedArrayList(User.CREATOR);
        this.myFollows = source.createTypedArrayList(User.CREATOR);
        this.myCollect = source.createTypedArrayList(CookBook.CREATOR);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.userId = in.readInt();
        this.nickName = in.readString();
        this.phome = in.readString();
        this.avatar = in.readString();
        this.password = in.readString();
        this.description = in.readString();
        this.fansCount = in.readInt();
        this.followCount = in.readInt();
        this.myCookBook = in.createTypedArrayList(CookBook.CREATOR);
        this.myFans = in.createTypedArrayList(User.CREATOR);
        this.myFollows = in.createTypedArrayList(User.CREATOR);
        this.myCollect = in.createTypedArrayList(CookBook.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}