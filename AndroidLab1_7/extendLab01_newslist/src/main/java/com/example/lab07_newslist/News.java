package com.example.lab07_newslist;

public class News {
    private String mTitle;
    private String mAuthor;
//    private String mContent;
    private int mImageId;

    public String getTitle() {
        return getmTitle();
    }

    public void setTitle(String title) {
        this.setmTitle(title);
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

//    public String getmContent() {
//        return mContent;
//    }
//
//    public void setmContent(String mContent) {
//        this.mContent = mContent;
//    }

    public int getmImageId() {
        return mImageId;
    }

    public void setmImageId(int mImageId) {
        this.mImageId = mImageId;
    }
}