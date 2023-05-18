package com.example.usermodual.beans;

import android.os.Parcel;



public class UrlImage extends SliderImage {
    private String url;
    private int placeHolder;

    public UrlImage(String url){
        this.url=url;
    }
    public String getImgUrl() {
        return url;
    }

    public void setImgUrl(String url) {
        this.url = url;
    }

    protected UrlImage(Parcel in) {
        url = in.readString();
        placeHolder=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(placeHolder);
    }
}
