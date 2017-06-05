package com.github.w_kamil.shoppingapp.dao;


import android.os.Parcel;
import android.os.Parcelable;

public class Shop implements Parcelable {

    private String identifier;
    private String address;

    public Shop(String identifier, String address) {
        this.identifier = identifier;
        this.address = address;
    }

    public Shop(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.identifier = data[0];
        this.address = data[1];

    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAddress() {
        return address;
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
        dest.writeStringArray(new String[]{this.identifier, this.address});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}
