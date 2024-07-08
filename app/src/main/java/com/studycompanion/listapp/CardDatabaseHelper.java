package com.studycompanion.listapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CardDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "card_wallet.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CARDS = "cards";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CARD_NAME = "card_name";
    public static final String COLUMN_CARD_NUMBER = "card_number";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_VALID_THRU = "valid_thru";
    public static final String COLUMN_CVV_CODE = "cvv_code";
    public static final String COLUMN_BACKGROUND_RES_ID = "background_res_id";



    // Create table query
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CARDS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CARD_NAME + " TEXT, " +
                    COLUMN_CARD_NUMBER + " TEXT, " +
                    COLUMN_USER_NAME + " TEXT, " +
                    COLUMN_VALID_THRU + " TEXT, " +
                    COLUMN_CVV_CODE + " TEXT, " +
                    COLUMN_BACKGROUND_RES_ID + " INTEGER);";


    public CardDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades if needed
    }
}
