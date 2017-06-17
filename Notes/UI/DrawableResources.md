# UI Polishing #

Drawable resources can be included in xml files; in foo.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<shape
	xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rectangle">
    <stroke android:width="2dp" android:color="@color/colorPrimaryLight" />
    <corners android:radius="5dp" />
</shape>
```

These can then be included in elements

```xml
 <ImageView
 	android:background="@drawable/foo/">
```

## Images ##

Images can be directly included via the srcCompat field

In mimap incldue a jpg or image file called myjpg

```xml
 <ImageView
    app:srcCompat="@mipmap/myjpg".>
```