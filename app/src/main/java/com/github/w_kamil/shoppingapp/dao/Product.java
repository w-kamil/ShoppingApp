package com.github.w_kamil.shoppingapp.dao;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Product implements Parcelable, Comparable<Product> {

    private int id;
    private final String barCode;
    private String description;


    public Product(String barCode, String description) {
        this.barCode = barCode;
        this.description = description;
    }

    public Product(int id, String barCode, String description) {
        this.id = id;
        this.barCode = barCode;
        this.description = description;
    }

    public Product(Parcel in) {
        // the order needs to be the same as in writeToParcel() method
        this.id = in.readInt();
        this.barCode = in.readString();
        this.description = in.readString();

    }

    public int getId() {
        return id;
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
    public String toString() {
        return description + " - " + barCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(barCode);
        dest.writeString(description);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };


    @Override
    public int compareTo(@NonNull Product o) {
        return this.description.compareTo(o.description);
    }
}

