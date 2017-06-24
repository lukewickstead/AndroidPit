# SQLite #

- https://developer.android.com/training/basics/data-storage/databases.html

## SQLiteOpenHelper ##

The SQLiteOpenHelper is used to deploy, upgrade and open the database. 

Increments to the database version trigger the onUpgrade to be run.

```java
public class XXXDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "XXXDbHelper.db";
    private static final int DATABASE_VERSION = 1;

    public XXXDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold waitlist data
        final String SQL_CREATE_TABLE = "CREATE TABLE.... ";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    	// This is used to upgrade the table through SQL statements
    }
}

```

## Data Model Definition ##

The BaseColumns class is sub classed to define the shape of the table. This defines the _ID column automatically.

```java
import android.provider.BaseColumns;
public class XXXContract {
    public static final class XXXEntry implements BaseColumns {
        public static final String TABLE_NAME = "XXX";
        public static final String COLUMN_ONE = "ColumnOne";
    }
}
```

## Reading Data ##

Data can be read via cursors

```java
 XXXDbHelper dbHelper = new XXXDbHelper(this);
private SQLiteDatabase db = dbHelper.getWritableDatabase();

Cursor cursor mDb.query(
    XXXContract.XXXEntry.TABLE_NAME,
    null,
    null,
    null,
    null,
    null,
    XXXContract.XXXEntry.COLUMN_TIMESTAMP

if (mCursor.moveToPosition(position)) {
    String name = mCursor.getString(mCursor.getColumnIndex(XXXContract.XXXEntry.COLUMN_ONE));
    long id = mCursor.getLong(mCursor.getColumnIndex(XXXContract.XXXEntry._ID));
}

// mCursor.moveToNext();
// mCursor.moveToFirst();

mCursor.close()
```

## Inserting Data ##

Data can be inserted through ContentValues

```java
List<ContentValues> list = new ArrayList<ContentValues>();

ContentValues cv = new ContentValues();
cv.put(XXXContract.XXXEntry.COLUMN_ONE, "OneTwo");
list.add(cv);

// .. put as many as you want

try
{
    db.beginTransaction();
    for(ContentValues c:list){
        db.insert(XXXContract.XXXEntry.TABLE_NAME, null, c);
    }
    db.setTransactionSuccessful();
}
catch (SQLException e) {
}
finally
{
    db.endTransaction();
}
```

## Deleting Data ##

All data can be deleted through the delete command.

```java
db.delete (WaitlistContract.WaitlistEntry.TABLE_NAME,null,null);
```


Certain records can be deleted with:


```java
db.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, WaitlistContract.WaitlistEntry._ID + "=" + id, null) > 0;
```
