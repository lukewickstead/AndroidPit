package com.wickstead.adaptivereminder.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class AdaptiveRemindersContract {

    private AdaptiveRemindersContract() {
    }

    public static final String AUTHORITY = "com.wickstead.adaptivereminder";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String REMINDERS_CONTENT_PATH = "reminders";
    public static final Uri REMINDERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(REMINDERS_CONTENT_PATH).build();

    public class Reminders implements BaseColumns {
        public static final String TABLE_NAME = "reminders";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ESTIMATED_PERIOD_MINUTES = "estimated_period_mins";
    }
}