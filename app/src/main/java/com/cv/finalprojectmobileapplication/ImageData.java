package com.cv.finalprojectmobileapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageData implements Parcelable {

    private String image, desc;

    public ImageData(String image, String desc) {
        this.image = image;
        this.desc = desc;
    }

    protected ImageData(Parcel in) {
        image = in.readString();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getDesc() {
        return desc;
    }
}
