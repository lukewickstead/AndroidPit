# Intents #

Intents are a messages requesting functionality to happen. They are handled asynchronously.

There are three types of intents:

- Explicit
- Implicit
- Pending

We have already seen intents by defining our Activity as being a launcher

```xml
<activity android:name="com.example.android.datafrominternet.MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
    </intent-filter>
</activity>
```

## Explicit Intents ##

An explicit intent knows what Activity it is requesting via it's class name.

- Declare the Activity within the manifest
	- The meta-data defines the parent activity to allow up navigation
- Start the activity via an intent

```xml
<activity
    android:name="com.foo.FooActivity"
    android:parentActivityName=".MainActivity">
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value=".MainActivity" />
</activity>
```

```java
startActivity(new Intent(this, FoodActivity.class));
```

## Implicit Intents ##

Implicit intents are those who register an action, the opening activity is decided by the system looking at which applications can perform the intent. Where multiple applications exist the user is asked to select one.

They define an intent with a predefined action.

```java
Uri webpage = Uri.parse("http://bbb.co.uk);
Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
if (intent.resolveActivity(getPackageManager()) != null) {
    startActivity(intent);
}
```

You can register an intent receiver along with it's filters within the manifest:

```xml
<activity android:name=".HelloWorld1" android:label="Hello World from IntentBasics">
    <intent-filter>
        <action android:name="com.pluralsight.action.HELLO_WORLD" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>

```

Any application can then request this action to be performed:

```java
startActivity(new Intent("com.pluralsight.action.HELLO_WORLD"));
```

## Passing Data With An Intent ##

Data can be passed via the intent's putExtra method.

```java
intent.putExtra(Intent.EXTRA_TEXT, "PassedData");
```

Under the hood this uses a bundle and is a short cut:

```java
Bundle data = new Bundle();
data.putString(Intent.EXTRA_TEXT, "PassedData");
intent.putExtras(b);
```

In the newly opened activity the extra data can be retrieved:

```java
Intent intent = getIntent();
if (intent.hasExtra(Intent.EXTRA_TEXT)) {
	String textEntered = intent.getStringExtra(Intent.EXTRA_TEXT);
}
```

You can get meta information from the intent.

```java
String action = intent.getAction();
Bundle extras = intent.getExtras();
if(extras != null){
   Set<String> keySet = extras.keySet();
   for(String key : keySet) {
       String value = extras.get(key).toString();
   }
}
```

## Intent Builder ##

The intent builder can be used to define complex intents

```java
ShareCompat.IntentBuilder
.from(this)
.setType("text/plain")
.setChooserTitle("Share Something")
.setText("textToShare")
.startChooser();
```

## Intent Filters ##

- https://developer.android.com/guide/components/intents-filters.html#Resolution

We provide filters to our intents to provide more control over wheter the activity can perform the intent.

We can filter in three ways:

- Action
- Data
- Category

The rules are accumulative and be be applied multiple times to produce complex filters and combinations of filters.

## Filter By Action ##

```xml
<intent-filter>
    <action android:name="android.intent.action.EDIT" />
    <action android:name="android.intent.action.VIEW" />
</intent-filter>
```

## Filter By Category ##

```xml
<intent-filter>
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
</intent-filter>
```

## Filter By Data ##

```xml
<intent-filter>
    <data android:mimeType="video/mpeg" android:scheme="http" />
    <data android:mimeType="audio/mpeg" android:scheme="http" />
</intent-filter>
```

Not all intents can be registered in the manifest as they require the application to be started; battery  changed and time tick for example.

## Common System Interactions ##

Useful code for interacting with bits of the system:

### Calling via the dial screen ###

```java
Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:012345678"));
startActivity(intent);
```

### Take a photo and return it ###

```java
Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

File targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

if(!directory.exists())
	directory.mkdirs();

intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(targetDir, "Picture.jpg")));
startActivityForResult(intent, CALLBACK_ID_CAMERA);
```

### Select and return a contact ###

```java
Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
startActivityForResult(intent, CALLBACK_ID_CAMERA);
```

When startActivityForResult is called, the callback onActivityResult is used.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(resultCode == Activity.RESULT_OK){
        switch (requestCode){
            case CALLBNACK_ID_CONTACT:
                break;
            case CALLBNACK_ID_CAMERA:
                break;
            default:
            	break;
        }
    }
}
```

### Requesting a map at a location ###

```java
String addressString = "Bristol, UK;
Uri.Builder builder = new Uri.Builder();
builder.scheme("geo").path("0,0").query(addressString);
Uri addressUri = builder.build();
Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setData(geoLocation);
if (intent.resolveActivity(getPackageManager()) != null) {
    startActivity(intent);
}
```