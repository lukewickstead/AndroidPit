package com.wickstead.adaptivereminder.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class RemindersDataContentProvider extends ContentProvider {

    private static final int REMINDERS = 100;
    private static final int REMINDERS_WITH_ID = 101;


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private RemindersDbHelper mRemindersDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mRemindersDbHelper = new RemindersDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {

        final SQLiteDatabase db = mRemindersDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case REMINDERS:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "_ID ASC";
                }
                break;
            case REMINDERS_WITH_ID:
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor = db.query(
                AdaptiveRemindersContract.Reminders.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        retCursor.setNotificationUri(
                getContext().getContentResolver(),
                uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(
            @NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(
            @NonNull Uri uri,
            @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(
            @NonNull Uri uri,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(
            @NonNull Uri uri,
            @Nullable ContentValues values,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {
        return 0;
    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // All reminders
        uriMatcher.addURI(
                AdaptiveRemindersContract.AUTHORITY,
                AdaptiveRemindersContract.REMINDERS_CONTENT_PATH,
                REMINDERS);

        // ReminderRepository by id
        uriMatcher.addURI(
                AdaptiveRemindersContract.AUTHORITY,
                AdaptiveRemindersContract.REMINDERS_CONTENT_PATH + "/#",
                REMINDERS_WITH_ID);

        return uriMatcher;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        mRemindersDbHelper.close();
    }

    // TODO: How can java packages have their internals visible to a test package
    public RemindersDbHelper getOpenHelperForTest() {
        return mRemindersDbHelper;
    }

}