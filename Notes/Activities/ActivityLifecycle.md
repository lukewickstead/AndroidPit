# Activity Lifecycle #

## Lifecycle Methods ##

The following methods are called in order.

- onCreate
	- Called when the activity is first created.
	- Do all static setup; create views, bind data etc.
- onStart
	- Called when the activity is becoming visible to the user.
- onResume
	- Called when the activity will start interacting with the user.
- onPause
	- Called when the system is about to start resuming a previous activity.
	- Use to persist data and stop any processes working on the CPU.
	- The next activity will not be resumed until this method has finished processing; so keep it quick!
- onStop
	- Called when the activity is no longer visible to the user; this activity is being paused or destroyed and a new one is coming into play.
- onRestart
	- Called after your activity has been stopped, prior to it being started again. The onStart and onResume will be called after this.
- onDestroy
	- The final call before an activity is destroyed due to the activity finishing or being temporarily destroying to save memory; distinguished by isFinishing().

##State and Application Lifecycle##

When a device is re-orientated the application is destroyed and then created. The same as if the application would have been shut and reopened.

Depending upon the view type, and if they have an id, some views will automatically persist their state.

- Automatically Persist State
	- EditText
	- Checkbox
	- Switch
	- RadioButton
- Do not automatically persist state
	- TextView
	- Button

###Persisting Sate###

You can manually persist the state by subclassing onSaveInstanceState and onRestoreInstanceState or onCreate. The state is persisted via a bundle (key value pair entity)

```java
@Override
protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (savedInstanceState != null) {
        txtFoo.setText(savedInstanceState.getString(KEY_FOO));
    }
}

@Override
protected void onCreate(Bundle savedInstanceState) {
   if (savedInstanceState != null) {
        if (savedInstanceState.containsKey(KEY_FOO)) {
        txtFoo.setText(savedInstanceState.getString(KEY_FOO));
        }
    }
}

@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("KEY", "SavedData");
}
```

##Handle Config Changes##

A final alternative is to handle the change of device orientation manually, thought this is discouraged:

Define which config changes is required

```xml
<activity
  android:name=".MyActivity"
  android:configChanges="orientation"
  android:label="@string/app_name">
<activity>
```

```java
@Override
protected void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(outState);
    // handle changes
}
```
