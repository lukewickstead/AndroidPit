# Content Provider #

## Reading Content ##

Content providers expose their data via the contentResolver and their URI.

```java
ContentResolver resolver = getContentResolver();
Cursor cursor = resolver.query(ProviderContract.CONTENT_URI, null, null, null, null);
```

## Creating Content Provider ##

A contract is used to defined the data contract

```java
public class XXXContract {

    public static final String AUTHORITY = "com.foo.XXXList";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_XXX = "XXX";

    public static final class TaskEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_XXX).build();
        public static final String TABLE_NAME = "XXX";
        public static final String COLUMN_ONE = "One";
    }
}
```

Override ContentProvider. The following example assumes the data is stored in a SQLite db accessed via the XXXDbHelper.

```java

public class XXXContentProvider extends ContentProvider {

    public static final int XXX = 100;
    public static final int XXX_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

		// Define matching criteria which our povider can respond to
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TaskContract.AUTHORITY, XXXContract.PATH_XXX, XXX);
        uriMatcher.addURI(TaskContract.AUTHORITY, XXXContract.PATH_XXX + "/#", XXX_WITH_ID);

        return uriMatcher;
    }

    private XXXDbHelper mTaskDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mXXXDbHelper = new XXXDbHelper(context);
        return true;
    }


    // Implement insert to handle requests to insert a single new row of data
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case XXX:
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(XXXContract.XXXEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mXXXDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case XXX:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int xxxDeleted;

        switch (match) {
            case XXX_WITH_ID:
                String id = uri.getPathSegments().get(1);
                xxxDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return xxxDeleted;
    }

// Implement Insert and Update overrides

```

We can then query, inswert, delete data as required via the content resolver.

## Insert Data ##

```java
ContentValues contentValues = new ContentValues();
contentValues.put(XXXContract.XXXEntry.COLUMN_ONE, input);
Uri uri = getContentResolver().insert(XXXContract.XXXEntry.CONTENT_URI, contentValues);
```

## Get all records ##

```java
return getContentResolver().query(
	XXXContract.XXXEntry.CONTENT_URI, null, null, null, XXXContract.XXXEntry.COLUMN_ONE);
```