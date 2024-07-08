package com.studycompanion.listapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OfficeDataBaseHelper extends SQLiteOpenHelper {
    public static final String OFFICE_DATABASE_NAME = "office_card_wallet.db";
    public static final int OFFICE_DATABASE_VERSION = 1;

    public static final String OFFICE_TABLE_CARDS = "cards";
    public static final String OFFICE_COLUMN_ID = "_id";
    public static final String OFFICE_COLUMN_CARD_NAME = "card_name";
    public static final String OFFICE_COLUMN_CARD_NUMBER = "card_number";
    public static final String OFFICE_COLUMN_USER_NAME = "user_name";
    public static final String OFFICECOLUMN_VALID_THRU = "valid_thru";
    public static final String OFFICE_COLUMN_CVV_CODE = "cvv_code";
    public static final String OFFICE_COLUMN_BACKGROUND_RES_ID = "background_res_id";



    // Create table query
    private static final String TABLE_CREATE =
            "CREATE TABLE " + OFFICE_TABLE_CARDS + " (" +
                    OFFICE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    OFFICE_COLUMN_CARD_NAME + " TEXT, " +
                    OFFICE_COLUMN_CARD_NUMBER + " TEXT, " +
                    OFFICE_COLUMN_USER_NAME + " TEXT, " +
                    OFFICECOLUMN_VALID_THRU + " TEXT, " +
                    OFFICE_COLUMN_CVV_CODE + " TEXT, " +
                    OFFICE_COLUMN_BACKGROUND_RES_ID + " INTEGER);";


    public OfficeDataBaseHelper(Context context) {
        super(context, OFFICE_DATABASE_NAME, null, OFFICE_DATABASE_VERSION);
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


