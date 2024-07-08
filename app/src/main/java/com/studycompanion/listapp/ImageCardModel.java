package com.studycompanion.listapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ImageCardModel implements Parcelable {

    // Uri for the image associated with the image card
    private Uri cardImageUri;

    // Flag to indicate whether the image is blurred
    private boolean blurred;

    // Parcelable creator
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_IMAGE_URI, cardImageUri.toString());
        return values;
    }
    public static ImageCardModel fromCursor(Cursor cursor) {
        try {
            // Check if the cursor is null or empty
            if (cursor == null || cursor.getCount() == 0) {
                Log.e("ImageCardModel", "Cursor is null or empty");
                return null;
            }

            // Check if the cursor contains the specified column
            int columnIndex = cursor.getColumnIndex(DBHelper.COLUMN_IMAGE_URI);
            if (columnIndex != -1) {
                // Get the URI string from the cursor
                String uriString = cursor.getString(columnIndex);

                // Check if the URI string is not null or empty
                if (uriString != null && !uriString.isEmpty()) {
                    // Parse the URI and create an ImageCardModel
                    Uri uri = Uri.parse(uriString);
                    return new ImageCardModel(uri);
                } else {
                    Log.e("ImageCardModel", "Image URI is null or empty");
                }
            } else {
                Log.e("ImageCardModel", "Column not found in cursor: " + DBHelper.COLUMN_IMAGE_URI);
            }
        } catch (Exception e) {
            Log.e("ImageCardModel", "Error while creating ImageCardModel from cursor", e);
        }

        // Return null if there's an issue
        return null;
    }


    public static final Creator<ImageCardModel> CREATOR = new Creator<ImageCardModel>() {
        @Override
        public ImageCardModel createFromParcel(Parcel in) {
            return new ImageCardModel(in);
        }

        @Override
        public ImageCardModel[] newArray(int size) {
            return new ImageCardModel[size];
        }
    };

    // Constructor
    public ImageCardModel(Uri cardImageUri) {
        this.cardImageUri = cardImageUri;
        this.blurred = false; // Default: Image is not blurred
    }

    // Parcelable constructor
    protected ImageCardModel(Parcel in) {
        cardImageUri = in.readParcelable(Uri.class.getClassLoader());
        blurred = in.readByte() != 0;
    }

    // Getter for cardImageUri
    public Uri getCardImageUri() {
        return cardImageUri;
    }

    // Setter for cardImageUri
    public void setCardImageUri(Uri cardImageUri) {
        this.cardImageUri = cardImageUri;
    }

    // Getter for blurred
    public boolean isBlurred() {
        return blurred;
    }

    // Setter for blurred
    public void setBlurred(boolean blurred) {
        this.blurred = blurred;
    }

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(cardImageUri, flags);
        dest.writeByte((byte) (blurred ? 1 : 0));
    }
}
