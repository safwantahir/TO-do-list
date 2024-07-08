package com.studycompanion.listapp;

import android.os.Parcel;
import android.os.Parcelable;

public class CardModel implements Parcelable {
    private long id;
    private String cardName;
    private String cardNumber;
    private String userName;
    private String validThru;
    private String cvvCode;
    private boolean isBlurred; // Add a boolean field for blurring
    private int backgroundResId = R.drawable.card1;


    public CardModel(String cardName, String cardNumber, String userName, String validThru, String cvvCode) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.validThru = validThru;
        this.cvvCode = cvvCode;
        this.isBlurred = false; // Default to not blurred
        this.backgroundResId= 0; // Default to no selected image
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getValidThru() {
        return validThru;
    }

    public void setValidThru(String validThru) {
        this.validThru = validThru;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public boolean isBlurred() {
        return isBlurred;
    }

    public void setBlurred(boolean blurred) {
        isBlurred = blurred;
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public void setBackgroundResId(int backgroundResId) {
        this.backgroundResId = backgroundResId;
    }
    public int getSelectedImageResId() {
        return backgroundResId;
    }

    public void setSelectedImageResId(int selectedImageResId) {
        this.backgroundResId = backgroundResId;
    }

    // Parcelable implementation
    protected CardModel(Parcel in) {
        cardName = in.readString();
        cardNumber = in.readString();
        userName = in.readString();
        validThru = in.readString();
        cvvCode = in.readString();
        isBlurred = in.readByte() != 0; // Read the boolean value
        backgroundResId= in.readInt();
    }

    public static final Creator<CardModel> CREATOR = new Creator<CardModel>() {
        @Override
        public CardModel createFromParcel(Parcel in) {
            return new CardModel(in);
        }

        @Override
        public CardModel[] newArray(int size) {
            return new CardModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cardName);
        dest.writeString(cardNumber);
        dest.writeString(userName);
        dest.writeString(validThru);
        dest.writeString(cvvCode);
        dest.writeByte((byte) (isBlurred ? 1 : 0)); // Write the boolean value
        dest.writeInt(backgroundResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setId(long id) {
        this.id = id;
    }
    public CardModel() {
        // Default constructor with no arguments
    }
    public long getId() {
        return id;
    }

    // Setter method for ID (if needed)

}
