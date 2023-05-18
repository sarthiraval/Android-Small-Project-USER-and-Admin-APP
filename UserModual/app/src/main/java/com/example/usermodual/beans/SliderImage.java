package com.example.usermodual.beans;

import android.os.Parcel;
import android.os.Parcelable;



public class SliderImage implements Parcelable {
    private int position;
    public SliderImage(){
    }

    protected SliderImage(Parcel in) {
        position = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SliderImage> CREATOR = new Creator<SliderImage>() {
        @Override
        public SliderImage createFromParcel(Parcel in) {
            return new SliderImage(in);
        }

        @Override
        public SliderImage[] newArray(int size) {
            return new SliderImage[size];
        }
    };


    }
