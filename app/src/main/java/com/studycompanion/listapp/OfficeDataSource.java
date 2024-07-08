package com.studycompanion.listapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class OfficeDataSource {
    private SQLiteDatabase officedatabase;
    private OfficeDataBaseHelper officedbHelper;

    public OfficeDataSource(Context context) {
        officedbHelper = new OfficeDataBaseHelper(context);
    }

    public void officeopen() throws SQLException {
        officedatabase = officedbHelper.getWritableDatabase();
    }

    public void officeclose() {
        officedbHelper.close();
    }

    public long oficeinsertCard(OfficeModel officecard) {
        ContentValues values = new ContentValues();
        values.put(OfficeDataBaseHelper.OFFICE_COLUMN_CARD_NAME, officecard.getofficeCardName());
        values.put(OfficeDataBaseHelper.OFFICE_COLUMN_CARD_NUMBER, officecard.getOfficecardNumber());
        values.put(OfficeDataBaseHelper.OFFICE_COLUMN_USER_NAME, officecard.getOfficeuserName());
        values.put(OfficeDataBaseHelper.OFFICECOLUMN_VALID_THRU, officecard.getOfficevalidThru());
        values.put(OfficeDataBaseHelper.OFFICE_COLUMN_CVV_CODE, officecard.getOfficecvvCode());
        values.put(OfficeDataBaseHelper.OFFICE_COLUMN_BACKGROUND_RES_ID, officecard.getOfficebackgroundResId());

        return officedatabase.insert(OfficeDataBaseHelper.OFFICE_TABLE_CARDS, null, values);
    }

    public List<OfficeModel> getAllofficeCards() {
        List<OfficeModel> cards = new ArrayList<>();
        Cursor officecursor = officedatabase.query(
                OfficeDataBaseHelper.OFFICE_TABLE_CARDS,
                null, // columns (null selects all)
                null, // selection
                null, // selectionArgs
                null, // groupBy
                null, // having
                null  // orderBy
        );

        if (officecursor != null) {
            officecursor.moveToFirst();
            while (!officecursor.isAfterLast()) {
                OfficeModel card = officecursorToCard(officecursor);
                cards.add(card);
                officecursor.moveToNext();
            }
            officecursor.close();
        }
        return cards;
    }

    @SuppressLint("Range")
    private OfficeModel officecursorToCard(Cursor officecursor) {
        OfficeModel card = new OfficeModel();
        card.setId(officecursor.getLong(officecursor.getColumnIndex(OfficeDataBaseHelper.OFFICE_COLUMN_ID)));
        card.officesetCardName(officecursor.getString(officecursor.getColumnIndex(OfficeDataBaseHelper.OFFICE_COLUMN_CARD_NAME)));
        card.setOfficecardNumber(officecursor.getString(officecursor.getColumnIndex(OfficeDataBaseHelper.OFFICE_COLUMN_CARD_NUMBER)));
        card.setOfficeuserName(officecursor.getString(officecursor.getColumnIndex(OfficeDataBaseHelper.OFFICE_COLUMN_USER_NAME)));
        card.setOfficevalidThru(officecursor.getString(officecursor.getColumnIndex(OfficeDataBaseHelper.OFFICECOLUMN_VALID_THRU)));
        card.setOfficecvvCode(officecursor.getString(officecursor.getColumnIndex(OfficeDataBaseHelper.OFFICE_COLUMN_CVV_CODE)));
        card.setOfficebackgroundResId(officecursor.getInt(officecursor.getColumnIndex(OfficeDataBaseHelper.OFFICE_COLUMN_BACKGROUND_RES_ID)));

        return card;
    }
    public void officedeleteCard(OfficeModel officecard) {
        SQLiteDatabase officedatabase = officedbHelper.getWritableDatabase();
        try {
            officedatabase.beginTransaction();
            // Define the WHERE clause to delete the officecard with a specific ID
            String whereClause = OfficeDataBaseHelper.OFFICE_COLUMN_ID + "=?";
            String[] whereArgs = {String.valueOf(officecard.officegetId())};
            // Delete the officecard
            int rowsDeleted = officedatabase.delete(OfficeDataBaseHelper.OFFICE_TABLE_CARDS, whereClause, whereArgs);
            if (rowsDeleted > 0) {
                officedatabase.setTransactionSuccessful(); // Only set as successful if the delete was successful
            } else {
                // Handle the case where the officecard wasn't found or couldn't be deleted
            }
        } catch (SQLException e) {
            // Handle the exception here
        } finally {
            officedatabase.endTransaction();
            officeclose();
        }
    }

}
