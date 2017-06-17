# Menu #

## Menu XML Definition ##

The menu is defined as an xml file.

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/action_search"
        android:orderInCategory="1"
        app:showAsAction="ifRoom"
        android:title="@string/search"/>
</menu>
```

- app:showAsAction set if the icon is set as a button in the menu
- android:orderInCategory is the order in the list

## Inflating Menus ##

Within the activity you inflate the menu in onCreateOptionsMenu and respond to events within onOpstionsItemSelected.


```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflats the menus
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
}
```

## Menus Actions ##

```java
@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// Handles the click event handler of the menu item
    int itemThatWasClickedId = item.getItemId();
    if (itemThatWasClickedId == R.id.action_search) {
        makeGithubSearchQuery();
        return true;
    }
    return super.onOptionsItemSelected(item);
}
```