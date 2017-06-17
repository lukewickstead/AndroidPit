# Logging #
## Log ##

```java
private static final String TAG = MainActivity.class.getSimpleName();

Log.v(TAG, "Verbose");
Log.d(TAG, "Debug");
Log.i(TAG, "Info");
Log.w(TAG, "Warning");
Log.e(TAG, "Error");
```

The thread and process id can be grabbed

```java
String logMessage = String.format("%s, Process ID:%d, Thread ID:%d", label, android.os.Process.myPid(), android.os.Process.myTid());
```

## Timber ##

Timber extends the default Android Log class.

- https://github.com/JakeWharton/timber

```xml
compile 'com.jakewharton.timber:timber:4.5.1'
```