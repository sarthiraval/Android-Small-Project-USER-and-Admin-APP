package com.example.usermodual.beans;

import android.graphics.drawable.Drawable;
import android.os.Parcel;

import androidx.annotation.DrawableRes;


public class DrawableImage extends SliderImage {
    private int drawable;

    public DrawableImage(@DrawableRes int drawable)
    {
        this.drawable = drawable;
    }

    public int getDrawable() {
        return drawable;
    }

    protected DrawableImage(Parcel in) {
        drawable = (int) in.readValue(Drawable.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(drawable);
    }

}
