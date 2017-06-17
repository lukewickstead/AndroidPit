#Themes and Styles#

Styles and Themese are declare the same but:

- Styles are applied to a particular View
- Themes are applied to an activity or application

The following themese exist:

- Theme.Light
	- For API 10 or below
- Theme.Holo.Light
	- From API 11 onwards
- Theme.Holo.DarkActionBar
	- From API 14 onwards
- Theme.AppCompat
	- From API 7 onwards
- Theme.Material
	- From API 21 onwards

##Default Theme##

Any number of styles and themes can be included in the styles.xml

If you overwrite the colourPrimary, colorPrimaryDark and colorAccent this will automatically style the android theme.

```xml
<resources>
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
</resources>
```

This can then be applied to any application, activity or view via the theme attribute

```xml
<Activity>
   android:theme="@style/AppTheme"
/>
```

##Inheritance##

Styles can use inheritance in explicit or implicit

```xml
<style name="inboxStyle" parent="folderStyle">
    <item name="android:textStyle">bold</item>
</style>

<style name="folderStyle.inboxStyle" >
    <item name="android:textStyle">bold</item>
</style>
```

##Styling Lists With List Item Selector##

Lists can have their themes defined by a selector;

```
// Set on linearlayout etc
android:background="@drawable/list_item_selector"
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@color/colorPrimaryLight" android:state_pressed="true" />
    <item android:drawable="@color/colorPrimaryLight" android:state_activated="true" />
    <item android:drawable="@color/colorPrimaryLight" android:state_selected="true" />
    <item android:drawable="@android:color/background_light" />
</selector>
```

##Styling Buttons With StateListDrawable##

In res/drawable/button_background_selector.xml

```xml
<selector>
    <item
    state_pressed=["true" | "false”] drawable=“@drawable/image1”
    state_focused=["true" | "false”] drawable=“@drawable/image2”
    state_hovered=["true" | "false"] drawable=“@drawable/image3” />
</selector>
```

In res/layout/activity_button.xml

```xml

<Button
android:background="@drawable/button_background_selector" />
```

##Styling Buttons With Button Colour Selector##

###ColorStateList###

res/color/button_color_selector.xml

```xml
<selector>
    <item
    state_pressed=["true" | "false”] color=“@color/red”
    state_focused=["true" | "false”] color=“@color/green”
    state_enabled=["true" | "false"] color=“@color/orange” />
</selector>
```

In res/layout/activity_main.xml

```xml
<Button
	android:textColor="@color/button_color_selector"/>
```

##Gradient Background##

```java
TextView txvGradient = (TextView) findViewById(R.id.txvGradient);
LinearGradient linearGradient = new LinearGradient(0, 0,
        0, txvGradient.getTextSize(), Color.GREEN, Color.RED, Shader.TileMode.MIRROR);
txvGradient.getPaint().setShader(linearGradient);
```

##Backward Compatability##

Resource files and directories can have filters applied to them in their names to target certain devices. The -VXX suffix can be used to target certain API versions.

ResourceDir-v21 to target min api version.

Alternatively you can do this in code.

```java
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
     // only for gingerbread and newer versions
}
```