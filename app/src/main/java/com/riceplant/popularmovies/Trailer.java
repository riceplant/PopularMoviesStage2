package com.riceplant.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {

    private String mKey;
    private String mId;
    private String mName;

    private Trailer(Parcel in) {
        mKey = in.readString();
        mId = in.readString();
        mName = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public Trailer() {
    }

    public String getKey() {
        return mKey;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mKey);
        parcel.writeString(mId);
        parcel.writeString(mName);
    }
}
