package com.mistershorr.databases;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {

    private int clumsiness, trustworthiness;
    private double gymFrequency, moneyOwed;
    private boolean isAwesome; //0 or 1
    private String name;

    public boolean isAwesome() {
        return isAwesome;
    }

    public void setAwesome(boolean awesome) {
        isAwesome = awesome;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    private String objectId;
    private String ownerId;

    public Friend() {
    }

    public int getClumsiness() {
        return clumsiness;
    }

    public void setClumsiness(int clumsiness) {
        this.clumsiness = clumsiness;
    }

    public int getTrustworthiness() {
        return trustworthiness;
    }

    public void setTrustworthiness(int trustworthiness) {
        this.trustworthiness = trustworthiness;
    }

    public double getGymFrequency() {
        return gymFrequency;
    }

    public void setGymFrequency(double gymFrequency) {
        this.gymFrequency = gymFrequency;
    }

    public double getMoneyOwed() {
        return moneyOwed;
    }

    public void setMoneyOwed(double moneyOwed) {
        this.moneyOwed = moneyOwed;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.clumsiness);
        dest.writeInt(this.trustworthiness);
        dest.writeDouble(this.gymFrequency);
        dest.writeDouble(this.moneyOwed);
        dest.writeByte(this.isAwesome ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeString(this.objectId);
        dest.writeString(this.ownerId);
    }

    protected Friend(Parcel in) {
        this.clumsiness = in.readInt();
        this.trustworthiness = in.readInt();
        this.gymFrequency = in.readDouble();
        this.moneyOwed = in.readDouble();
        this.isAwesome = in.readByte() != 0;
        this.name = in.readString();
        this.objectId = in.readString();
        this.ownerId = in.readString();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}