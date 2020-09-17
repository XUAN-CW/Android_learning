package com.example.lab14_newslist3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String mSource) {
        this.mSource = mSource;
    }

    public String getPicUrl() {
        return mPicUrl;
    }

    public void setPicUrl(String mPicUrl) {
        this.mPicUrl = mPicUrl;
    }

    public String getContentUrl() {
        return mContentUrl;
    }

    public void setContentUrl(String mContentUrl) {
        this.mContentUrl = mContentUrl;
    }

    public Integer getId() {
        return mId;
    }

    public String getDate() {
        return mPublishTime;
    }

    public News() {
    }

    @Expose(serialize = false , deserialize = false)
    private Integer mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("description")
    private String mSource;

    @SerializedName("picUrl")
    private String mPicUrl;

    @SerializedName("url")
    private String mContentUrl;

    @SerializedName("ctime")
    private String mPublishTime;

}
