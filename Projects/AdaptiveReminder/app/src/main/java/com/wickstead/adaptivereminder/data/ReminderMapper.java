package com.wickstead.adaptivereminder.data;

import android.database.Cursor;

import java.util.ArrayList;

public class ReminderMapper {

    public static ArrayList<Reminder> MapReminders(Cursor cursor) {

        ArrayList<Reminder> reminders = new ArrayList<>();

        cursor.move(0);
        while (cursor.moveToNext()) {
            reminders.add(MapReminder(cursor));
        }

        return reminders;
    }

    public static Reminder MapReminder(Cursor cursor) {

        int colId = cursor.getColumnIndex(AdaptiveRemindersContract.Reminders._ID);
        int colName = cursor.getColumnIndex(AdaptiveRemindersContract.Reminders.COLUMN_NAME);
        int colEstPrdMins = cursor.getColumnIndex(AdaptiveRemindersContract.Reminders.COLUMN_ESTIMATED_PERIOD_MINUTES);

        return new Reminder(
                cursor.getLong(colId),
                cursor.getString(colName),
                cursor.getInt(colEstPrdMins));
    }
}
