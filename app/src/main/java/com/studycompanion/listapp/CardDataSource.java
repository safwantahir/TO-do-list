package com.studycompanion.listapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class CardDataSource {
    private SQLiteDatabase database;
    private CardDatabaseHelper dbHelper;

    public CardDataSource(Context context) {
        dbHelper = new CardDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertCard(CardModel card) {
        ContentValues values = new ContentValues();
        values.put(CardDatabaseHelper.COLUMN_CARD_NAME, card.getCardName());
        values.put(CardDatabaseHelper.COLUMN_CARD_NUMBER, card.getCardNumber());
        values.put(CardDatabaseHelper.COLUMN_USER_NAME, card.getUserName());
        values.put(CardDatabaseHelper.COLUMN_VALID_THRU, card.getValidThru());
        values.put(CardDatabaseHelper.COLUMN_CVV_CODE, card.getCvvCode());
        values.put(CardDatabaseHelper.COLUMN_BACKGROUND_RES_ID, card.getBackgroundResId());

        return database.insert(CardDatabaseHelper.TABLE_CARDS, null, values);
    }

    public List<CardModel> getAllCards() {
        List<CardModel> cards = new ArrayList<>();
        Cursor cursor = database.query(
                CardDatabaseHelper.TABLE_CARDS,
                null, // columns (null selects all)
                null, // selection
                null, // selectionArgs
                null, // groupBy
                null, // having
                null  // orderBy
        );

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CardModel card = cursorToCard(cursor);
                cards.add(card);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return cards;
    }

    @SuppressLint("Range")
    private CardModel cursorToCard(Cursor cursor) {
        CardModel card = new CardModel();
        card.setId(cursor.getLong(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_ID)));
        card.setCardName(cursor.getString(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_CARD_NAME)));
        card.setCardNumber(cursor.getString(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_CARD_NUMBER)));
        card.setUserName(cursor.getString(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_USER_NAME)));
        card.setValidThru(cursor.getString(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_VALID_THRU)));
        card.setCvvCode(cursor.getString(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_CVV_CODE)));
        card.setBackgroundResId(cursor.getInt(cursor.getColumnIndex(CardDatabaseHelper.COLUMN_BACKGROUND_RES_ID)));

        return card;
    }
    public void deleteCard(CardModel card) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            database.beginTransaction();
            // Define the WHERE clause to delete the card with a specific ID
            String whereClause = CardDatabaseHelper.COLUMN_ID + "=?";
            String[] whereArgs = {String.valueOf(card.getId())};
            // Delete the card
            int rowsDeleted = database.delete(CardDatabaseHelper.TABLE_CARDS, whereClause, whereArgs);
            if (rowsDeleted > 0) {
                database.setTransactionSuccessful(); // Only set as successful if the delete was successful
            } else {
                // Handle the case where the card wasn't found or couldn't be deleted
            }
        } catch (SQLException e) {
            // Handle the exception here
        } finally {
            database.endTransaction();
            close();
        }
    }
}
