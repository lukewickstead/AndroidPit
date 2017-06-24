package com.wickstead.adaptivereminder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RemindersDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Reminders.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_REMINDERS =
            "CREATE TABLE " + AdaptiveRemindersContract.Reminders.TABLE_NAME + " (" +
                    AdaptiveRemindersContract.Reminders._ID + " INTEGER PRIMARY KEY," +
                    AdaptiveRemindersContract.Reminders.COLUMN_NAME + " TEXT," +
                    AdaptiveRemindersContract.Reminders.COLUMN_ESTIMATED_PERIOD_MINUTES + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AdaptiveRemindersContract.Reminders.TABLE_NAME;

    public RemindersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_REMINDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
