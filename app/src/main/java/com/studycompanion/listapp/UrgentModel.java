package com.studycompanion.listapp;

import android.os.Parcel;
import android.os.Parcelable;

public class UrgentModel implements Parcelable {
    private long id;
    private String urgentcardName;
    private String urgentcardNumber;
    private String urgentuserName;
    private String urgentvalidThru;
    private String urgentcvvCode;
    private boolean isBlurred; // Add a boolean field for blurring
    private int urgentbackgroundResId = R.drawable.card1;


    public UrgentModel(String urgentcardName, String urgentcardNumber, String urgentuserName, String urgentvalidThru, String urgentcvvCode) {
        this.urgentcardName = urgentcardName;
        this.urgentcardNumber = urgentcardNumber;
        this.urgentuserName = urgentuserName;
        this.urgentvalidThru = urgentvalidThru;
        this.urgentcvvCode = urgentcvvCode;
        this.isBlurred = false; // Default to not blurred
        this.urgentbackgroundResId = 0; // Default to no selected image
    }

    public String geturgentCardName() {
        return urgentcardName;
    }

    public void urgentsetCardName(String cardName) {
        this.urgentcardName = cardName;
    }

    public String getUrgentcardNumber() {
        return urgentcardNumber;
    }

    public void setUrgentcardNumber(String urgentcardNumber) {
        this.urgentcardNumber = urgentcardNumber;
    }

    public String getUrgentuserName() {
        return urgentuserName;
    }

    public void setUrgentuserName(String urgentuserName) {
        this.urgentuserName = urgentuserName;
    }

    public String getUrgentvalidThru() {
        return urgentvalidThru;
    }

    public void setUrgentvalidThru(String urgentvalidThru) {
        this.urgentvalidThru = urgentvalidThru;
    }

    public String getUrgentcvvCode() {
        return urgentcvvCode;
    }

    public void setUrgentcvvCode(String urgentcvvCode) {
        this.urgentcvvCode = urgentcvvCode;
    }

    public boolean isBlurred() {
        return isBlurred;
    }

    public void setBlurred(boolean blurred) {
        isBlurred = blurred;
    }

    public int getUrgentbackgroundResId() {
        return urgentbackgroundResId;
    }

    public void setUrgentbackgroundResId(int urgentbackgroundResId) {
        this.urgentbackgroundResId = urgentbackgroundResId;
    }
    public int getSelectedImageResId() {
        return urgentbackgroundResId;
    }

    public void setSelectedImageResId(int selectedImageResId) {
        this.urgentbackgroundResId = urgentbackgroundResId;
    }

    // Parcelable implementation
    protected UrgentModel(Parcel in) {
        urgentcardName = in.readString();
        urgentcardNumber = in.readString();
        urgentuserName = in.readString();
        urgentvalidThru = in.readString();
        urgentcvvCode = in.readString();
        isBlurred = in.readByte() != 0; // Read the boolean value
        urgentbackgroundResId = in.readInt();
    }

    public static final Creator<UrgentModel> CREATOR = new Creator<UrgentModel>() {
        @Override
        public UrgentModel createFromParcel(Parcel in) {
            return new UrgentModel(in);
        }

        @Override
        public UrgentModel[] newArray(int size) {
            return new UrgentModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(urgentcardName);
        dest.writeString(urgentcardNumber);
        dest.writeString(urgentuserName);
        dest.writeString(urgentvalidThru);
        dest.writeString(urgentcvvCode);
        dest.writeByte((byte) (isBlurred ? 1 : 0)); // Write the boolean value
        dest.writeInt(urgentbackgroundResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setId(long id) {
        this.id = id;
    }
    public UrgentModel() {
        // Default constructor with no arguments
    }
    public long urgentgetId() {
        return id;
    }

    // Setter method for ID (if needed)

}
