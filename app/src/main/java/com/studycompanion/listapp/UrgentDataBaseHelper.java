package com.studycompanion.listapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UrgentDataBaseHelper extends SQLiteOpenHelper {
    public static final String URGENT_DATABASE_NAME = "urgent_card_wallet.db";
    public static final int URGENT_DATABASE_VERSION = 1;

    public static final String URGENT_TABLE_CARDS = "cards";
    public static final String URGENT_COLUMN_ID = "_id";
    public static final String URGENT_COLUMN_CARD_NAME = "card_name";
    public static final String URGENT_COLUMN_CARD_NUMBER = "card_number";
    public static final String URGENT_COLUMN_USER_NAME = "user_name";
    public static final String URGENTCOLUMN_VALID_THRU = "valid_thru";
    public static final String URGENT_COLUMN_CVV_CODE = "cvv_code";
    public static final String URGENT_COLUMN_BACKGROUND_RES_ID = "background_res_id";



    // Create table query
    private static final String TABLE_CREATE =
            "CREATE TABLE " + URGENT_TABLE_CARDS + " (" +
                    URGENT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    URGENT_COLUMN_CARD_NAME + " TEXT, " +
                    URGENT_COLUMN_CARD_NUMBER + " TEXT, " +
                    URGENT_COLUMN_USER_NAME + " TEXT, " +
                    URGENTCOLUMN_VALID_THRU + " TEXT, " +
                    URGENT_COLUMN_CVV_CODE + " TEXT, " +
                    URGENT_COLUMN_BACKGROUND_RES_ID + " INTEGER);";


    public UrgentDataBaseHelper(Context context) {
        super(context, URGENT_DATABASE_NAME, null, URGENT_DATABASE_VERSION);
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


