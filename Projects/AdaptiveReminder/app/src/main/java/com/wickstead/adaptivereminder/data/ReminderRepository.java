package com.wickstead.adaptivereminder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.wickstead.adaptivereminder.data.AdaptiveRemindersContract.Reminders;

public class ReminderRepository {

    final private RemindersDbHelper dbHelper;

    final private static String[] sAllFields = {
            Reminders._ID,
            Reminders.COLUMN_NAME,
            Reminders.COLUMN_ESTIMATED_PERIOD_MINUTES
    };

    final private static String sIdQuery = Reminders._ID + " = ?";

    final private static String sIdAsc = Reminders._ID + " ASC";

    public ReminderRepository(Context context) {
        dbHelper = new RemindersDbHelper(context);
    }

    public ReminderRepository(RemindersDbHelper remindersDbHelper) {
        dbHelper = remindersDbHelper;
    }

    public long Insert(String name, int estimatedPeriodMins) {

        ContentValues values = getContentValues(name, estimatedPeriodMins);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.insert(
                Reminders.TABLE_NAME,
                null,
                values);
    }

    public int Update(long id, String name, int estimatedPeriodMins) {

        ContentValues values = getContentValues(name, estimatedPeriodMins);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.update(
                Reminders.TABLE_NAME,
                values,
                sIdQuery,
                asStringArray(id));
    }

    public Cursor GetAll() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(
                Reminders.TABLE_NAME,
                sAllFields,
                null,
                null,
                null,
                null,
                sIdAsc
        );
    }

    public Cursor GetById(long id) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(
                Reminders.TABLE_NAME,
                sAllFields,
                sIdQuery,
                asStringArray(id),
                null,
                null,
                sIdAsc
        );
    }

    public int DeleteAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.delete(Reminders.TABLE_NAME, null, null);
    }

    public int DeleteById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.delete(
                Reminders.TABLE_NAME,
                sIdQuery,
                asStringArray(id));
    }

    @NonNull
    private String[] asStringArray(long id) {
        return new String[]{Long.toString(id)};
    }

    @NonNull
    private ContentValues getContentValues(String name, int estimatedPeriodMins) {
        ContentValues values = new ContentValues();
        values.put(Reminders.COLUMN_NAME, name);
        values.put(Reminders.COLUMN_ESTIMATED_PERIOD_MINUTES, estimatedPeriodMins);
        return values;
    }
}