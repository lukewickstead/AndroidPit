#Shared Preferences#

##Shared Preferences##

Android can help with saving settings or shared preferences.

Call PreferenceManager.getDefaultSharedPreferences(context) to access an activities unique preference file. Where preferences are to be shared between activities you need to define the settings file name. Use sharedPreferences(name, mode).

The file will be stored in the applications file are and will only be accessible by your application.

##Default Shared Preferences##

Default shared preferences can be used to save value types as key value pairs.


```java
public final class PreferenceUtilities {

    public static final String KEY_XXX = "XXXKEY";

    synchronized private static void setXXXCount(Context context, int xxxCount) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_XXX, xxxCount);
        editor.apply();
    }

    public static int getXXXCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int xxxCount = prefs.getInt(KEY_XXX, 0);
        return xxxCount;
    }

    synchronized public static void incrementXXXCount(Context context) {
        int xxxCount = PreferenceUtilities.getXXXCount(context);
        PreferenceUtilities.setXXXCount(context, ++xxxCount);
    }
}
```

SharedPreferences.Editor has two methods apply and commit depending upon if you want to persist the data asynchronously or not.

The function editor.clear() can be used to remove all entries

The function editor.remove(String key); can be used to remove one entry based upon its key.

##Gson and Preferences##

The third party library Gson can be used to serialise reference types to strings to save in the shared preference file.

```java
Gson gson = new Gson();
String jsonString = gson.toJson(employeeObject, Employee.class);
Employee empObj = gson.fromJson(jsonString, Employee.class);
```

##Preference From Resource File##

Android can inflate settings from a layout file and manage the saving of them automatically.

In the activity inflate the settings file as if it is a layout.

```java
public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = this.getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
```

The settings layout file res/layout/activity_settings defines a fragment.

```xml
<?xml version="1.0" encoding="utf-8"?>
<fragment xmlns:android="http://schemas.android.com/apk/res/android"
          android:id="@+id/activity_settings"
          android:name="android.example.com.visualizerpreferences.SettingsFragment"
          android:layout_width="match_parent"
          android:layout_height="match_parent"/>

```

The preference screen is defined in an xml file; XXXSettings.xml.

There are a number of ui elements specially defined for saving to shared preferences

```xml
<?xml version="1.0" encoding="utf-8"?>

<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">

    <CheckBoxPreference
        android:defaultValue="@bool/pref_show_bass_default"
        android:key="@string/pref_show_bass_key"
        android:summaryOff="@string/pref_show_false"
        android:summaryOn="@string/pref_show_true"
        android:title="@string/pref_show_bass_label" />

    <EditTextPreference
        android:defaultValue="@string/pref_size_default"
        android:key="@string/pref_size_key"
        android:title="@string/pref_size_label" />

    <ListPreference
        android:defaultValue="@string/pref_color_red_value"
        android:entries="@array/pref_color_option_labels"
        android:entryValues="@array/pref_color_option_values"
        android:key="@string/pref_color_key"
        android:title="@string/pref_color_label" />

</PreferenceScreen>

```

The settings file is then inflated within the fragment.

We can also register change notification to allow handling of saving preferences, for example to validate them or to perform some action.

```java
public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        addPreferencesFromResource(R.xml.XXXSettings);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }

        Preference preference = findPreference(getString(R.string.pref_size_key));
        preference.setOnPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (null != preference) {
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                   preference.setSummary(value);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
    	// Use to intercept changes to validate them
        // Return true/false if accepted
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
```