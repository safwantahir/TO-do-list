package com.studycompanion.listapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UrgentDataSource {
    private SQLiteDatabase urgentdatabase;
    private UrgentDataBaseHelper urgentdbHelper;

    public UrgentDataSource(Context context) {
        urgentdbHelper = new UrgentDataBaseHelper(context);
    }

    public void urgenteopen() throws SQLException {
        urgentdatabase = urgentdbHelper.getWritableDatabase();
    }

    public void urgentclose() {
        urgentdbHelper.close();
    }

    public long urgentinsertCard(UrgentModel urgentcard) {
        ContentValues values = new ContentValues();
        values.put(UrgentDataBaseHelper.URGENT_COLUMN_CARD_NAME, urgentcard.geturgentCardName());
        values.put(UrgentDataBaseHelper.URGENT_COLUMN_CARD_NUMBER, urgentcard.getUrgentcardNumber());
        values.put(UrgentDataBaseHelper.URGENT_COLUMN_USER_NAME, urgentcard.getUrgentuserName());
        values.put(UrgentDataBaseHelper.URGENTCOLUMN_VALID_THRU, urgentcard.getUrgentvalidThru());
        values.put(UrgentDataBaseHelper.URGENT_COLUMN_CVV_CODE, urgentcard.getUrgentcvvCode());
        values.put(UrgentDataBaseHelper.URGENT_COLUMN_BACKGROUND_RES_ID, urgentcard.getUrgentbackgroundResId());

        return urgentdatabase.insert(UrgentDataBaseHelper.URGENT_TABLE_CARDS, null, values);
    }

    public List<UrgentModel> getAllurgentCards() {
        List<UrgentModel> cards = new ArrayList<>();
        Cursor urgentcursor = urgentdatabase.query(
                UrgentDataBaseHelper.URGENT_TABLE_CARDS,
                null, // columns (null selects all)
                null, // selection
                null, // selectionArgs
                null, // groupBy
                null, // having
                null  // orderBy
        );

        if (urgentcursor != null) {
            urgentcursor.moveToFirst();
            while (!urgentcursor.isAfterLast()) {
                UrgentModel card = urgentcursorToCard(urgentcursor);
                cards.add(card);
                urgentcursor.moveToNext();
            }
            urgentcursor.close();
        }
        return cards;
    }

    @SuppressLint("Range")
    private UrgentModel urgentcursorToCard(Cursor urgentcursor) {
        UrgentModel card = new UrgentModel();
        card.setId(urgentcursor.getLong(urgentcursor.getColumnIndex(UrgentDataBaseHelper.URGENT_COLUMN_ID)));
        card.urgentsetCardName(urgentcursor.getString(urgentcursor.getColumnIndex(UrgentDataBaseHelper.URGENT_COLUMN_CARD_NAME)));
        card.setUrgentcardNumber(urgentcursor.getString(urgentcursor.getColumnIndex(UrgentDataBaseHelper.URGENT_COLUMN_CARD_NUMBER)));
        card.setUrgentuserName(urgentcursor.getString(urgentcursor.getColumnIndex(UrgentDataBaseHelper.URGENT_COLUMN_USER_NAME)));
        card.setUrgentvalidThru(urgentcursor.getString(urgentcursor.getColumnIndex(UrgentDataBaseHelper.URGENTCOLUMN_VALID_THRU)));
        card.setUrgentcvvCode(urgentcursor.getString(urgentcursor.getColumnIndex(UrgentDataBaseHelper.URGENT_COLUMN_CVV_CODE)));
        card.setUrgentbackgroundResId(urgentcursor.getInt(urgentcursor.getColumnIndex(UrgentDataBaseHelper.URGENT_COLUMN_BACKGROUND_RES_ID)));

        return card;
    }
    public void urgentdeleteCard(UrgentModel urgentcard) {
        SQLiteDatabase urgentdatabase = urgentdbHelper.getWritableDatabase();
        try {
            urgentdatabase.beginTransaction();
            // Define the WHERE clause to delete the officecard with a specific ID
            String whereClause = UrgentDataBaseHelper.URGENT_COLUMN_ID + "=?";
            String[] whereArgs = {String.valueOf(urgentcard.urgentgetId())};
            // Delete the officecard
            int rowsDeleted = urgentdatabase.delete(UrgentDataBaseHelper.URGENT_TABLE_CARDS, whereClause, whereArgs);
            if (rowsDeleted > 0) {
                urgentdatabase.setTransactionSuccessful(); // Only set as successful if the delete was successful
            } else {
                // Handle the case where the officecard wasn't found or couldn't be deleted
            }
        } catch (SQLException e) {
            // Handle the exception here
        } finally {
            urgentdatabase.endTransaction();
            urgentclose();
        }
    }


}
