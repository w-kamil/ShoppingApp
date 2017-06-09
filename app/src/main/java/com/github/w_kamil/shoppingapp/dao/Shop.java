package com.github.w_kamil.shoppingapp.dao;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Shop implements Parcelable, Comparable<Shop> {

    private int id;
    private String identifier;
    private String address;

    public Shop(String identifier, String address) {
        this.identifier = identifier;
        this.address = address;
    }

    public Shop(int id, String identifier, String address) {
        this.id = id;
        this.identifier = identifier;
        this.address = address;
    }

    public Shop(Parcel in) {

        // the order needs to be the same as in writeToParcel() method
        this.id = in.readInt();
        this.identifier = in.readString();
        this.address = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getAddress() {
        return address;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(identifier);
        dest.writeString(address);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    @Override
    public int compareTo(@NonNull Shop o) {
        return this.identifier.compareTo(o.identifier);
    }
}
