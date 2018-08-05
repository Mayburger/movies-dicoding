package com.nacoda.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import lombok.Data;

/**
 * Created by Mayburger on 1/11/18.
 */

@Data
public class Detail implements Parcelable {
    private String revenue;
    private String runtime;
    private String tagline;
    private String budget;

    public Detail(JSONObject object) {

        try {

            String revenue = object.getString("revenue");
            String runtime = object.getString("runtime");
            String tagline = object.getString("tagline");
            String budget = object.getString("budget");

            this.revenue = revenue;
            this.runtime = runtime;
            this.tagline = tagline;
            this.budget = budget;

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.revenue);
        dest.writeString(this.runtime);
        dest.writeString(this.tagline);
        dest.writeString(this.budget);
    }

    protected Detail(Parcel in) {
        this.revenue = in.readString();
        this.runtime = in.readString();
        this.tagline = in.readString();
        this.budget = in.readString();
    }

    public static final Parcelable.Creator<Detail> CREATOR = new Parcelable.Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel source) {
            return new Detail(source);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };
}
