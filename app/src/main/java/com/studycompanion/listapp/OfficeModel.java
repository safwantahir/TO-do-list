package com.studycompanion.listapp;

import android.os.Parcel;
import android.os.Parcelable;

public class OfficeModel implements Parcelable {
    private long id;
    private String officecardName;
    private String officecardNumber;
    private String officeuserName;
    private String officevalidThru;
    private String officecvvCode;
    private boolean isBlurred; // Add a boolean field for blurring
    private int officebackgroundResId = R.drawable.card1;


    public OfficeModel(String officecardName, String officecardNumber, String officeuserName, String officevalidThru, String officecvvCode) {
        this.officecardName = officecardName;
        this.officecardNumber = officecardNumber;
        this.officeuserName = officeuserName;
        this.officevalidThru = officevalidThru;
        this.officecvvCode = officecvvCode;
        this.isBlurred = false; // Default to not blurred
        this.officebackgroundResId = 0; // Default to no selected image
    }

    public String getofficeCardName() {
        return officecardName;
    }

    public void officesetCardName(String cardName) {
        this.officecardName = cardName;
    }

    public String getOfficecardNumber() {
        return officecardNumber;
    }

    public void setOfficecardNumber(String officecardNumber) {
        this.officecardNumber = officecardNumber;
    }

    public String getOfficeuserName() {
        return officeuserName;
    }

    public void setOfficeuserName(String officeuserName) {
        this.officeuserName = officeuserName;
    }

    public String getOfficevalidThru() {
        return officevalidThru;
    }

    public void setOfficevalidThru(String officevalidThru) {
        this.officevalidThru = officevalidThru;
    }

    public String getOfficecvvCode() {
        return officecvvCode;
    }

    public void setOfficecvvCode(String officecvvCode) {
        this.officecvvCode = officecvvCode;
    }

    public boolean isBlurred() {
        return isBlurred;
    }

    public void setBlurred(boolean blurred) {
        isBlurred = blurred;
    }

    public int getOfficebackgroundResId() {
        return officebackgroundResId;
    }

    public void setOfficebackgroundResId(int officebackgroundResId) {
        this.officebackgroundResId = officebackgroundResId;
    }
    public int getSelectedImageResId() {
        return officebackgroundResId;
    }

    public void setSelectedImageResId(int selectedImageResId) {
        this.officebackgroundResId = officebackgroundResId;
    }

    // Parcelable implementation
    protected OfficeModel(Parcel in) {
        officecardName = in.readString();
        officecardNumber = in.readString();
        officeuserName = in.readString();
        officevalidThru = in.readString();
        officecvvCode = in.readString();
        isBlurred = in.readByte() != 0; // Read the boolean value
        officebackgroundResId = in.readInt();
    }

    public static final Creator<OfficeModel> CREATOR = new Creator<OfficeModel>() {
        @Override
        public OfficeModel createFromParcel(Parcel in) {
            return new OfficeModel(in);
        }

        @Override
        public OfficeModel[] newArray(int size) {
            return new OfficeModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(officecardName);
        dest.writeString(officecardNumber);
        dest.writeString(officeuserName);
        dest.writeString(officevalidThru);
        dest.writeString(officecvvCode);
        dest.writeByte((byte) (isBlurred ? 1 : 0)); // Write the boolean value
        dest.writeInt(officebackgroundResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setId(long id) {
        this.id = id;
    }
    public OfficeModel() {
        // Default constructor with no arguments
    }
    public long officegetId() {
        return id;
    }

    // Setter method for ID (if needed)

}
