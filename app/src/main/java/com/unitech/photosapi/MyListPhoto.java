package com.unitech.photosapi;


public class MyListPhoto {
    String mUrl,mTitle,thumbUrl,imgId,fav;

    public MyListPhoto() {
    }

    public MyListPhoto(String mUrl, String mTitle, String thumbUrl, String imgId, String fav) {
        this.mUrl = mUrl;
        this.mTitle = mTitle;
        this.thumbUrl = thumbUrl;
        this.imgId = imgId;
        this.fav = fav;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }
}
