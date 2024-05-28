package com.example.proj_appfitnes_simple;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.proj_appfitnes_simple.UserContract.UserEntry;

public class UserDbHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "users.db";

    // Table creation statements
    private static final String TABLE_CREATE_USER =
            "CREATE TABLE " + UserEntry.TABLE_USER + " (" +
                    UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UserEntry.COLUMN_USER_NAME + " TEXT, " +
                    UserEntry.COLUMN_USER_EMAIL + " TEXT, " +
                    UserEntry.COLUMN_USER_PASSWORD + " TEXT, " +
                    UserEntry.COLUMN_USER_WEIGHT + " REAL, " +
                    UserEntry.COLUMN_USER_HEIGHT + " REAL, " +
                    UserEntry.COLUMN_USER_AGE + " INTEGER" +
                    ");";

    private static final String TABLE_CREATE_DATOS =
            "CREATE TABLE " + UserEntry.TABLE_DATOS + " (" +
                    UserEntry.COLUMN_DATOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    UserEntry.COLUMN_DATOS_USER_ID + " INTEGER, " +
                    UserEntry.COLUMN_DATOS_CALORIA_ING + " INTEGER, " +
                    UserEntry.COLUMN_DATOS_CALORIA_QUEM + " INTEGER, " +
                    UserEntry.COLUMN_DATOS_LITROS_ING + " REAL, " +
                    UserEntry.COLUMN_DATOS_DIF_CALORICAS + " INTEGER, " +
                    "FOREIGN KEY(" + UserEntry.COLUMN_DATOS_USER_ID + ") REFERENCES " + UserEntry.TABLE_USER + "(" + UserEntry.COLUMN_USER_ID + ")" +
                    ");";

    public UserDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(TABLE_CREATE_DATOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_DATOS);
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_USER);
        onCreate(db);
    }
}
