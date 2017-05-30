package com.github.w_kamil.shoppingapp.dao;


import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private final String barCode;
    private String description;


    public Product(String barCode, String description) {
        this.barCode = barCode;
        this.description = description;
    }

    public Product(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.barCode = data[0];
        this.description = data[1];

    }

    public String getBarCode() {
        return barCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.barCode, this.description});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}

