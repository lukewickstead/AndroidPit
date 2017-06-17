#Widgets#

- https://developer.android.com/guide/topics/appwidgets/index.html
- https://developer.android.com/guide/practices/ui_guidelines/widget_design.html

##Creating##

- Widget provider class extending AppWidgetProvider
- WidgetProviderInfo.xml
    - UpdatePeriodMillis is the refresh or update period. The min is 30 minutes. This is in milliseconds
- Xml Layout
- Register widget as a BroadcastReceiver

In Android Studio; right click on app and select New -> Widget --> App Widget.

This will create framework skeleton code for the AppWidgetProvider and  the provider info XML. Also registers in the manifest 

##Remote Views##

Widget layouts are based on RemoteViews as they are separate to the android application.
Not every view type can be added.
They contain the complete view hierarchy.

- Use setTextViewText takes id and the text; not findViewById etc

AppWidgetProvider.onUpdate is called when the widget is created and also updated after the polling time. It should go through all the widget instances and updates them. 

AppWidgetManager class gets information on all instances of our widget. 

RemoteViews have their click event set by views.setOnClickPendingIntent(R.id.id_of_widget_element, pending Intent) 

##Widget Margins## 

Since API 14 widgets automatically get margins between other margins. We can use the values-14 to target devices on API 14 and greater. 

##Background Tasks## 

Click events on widgets can only raise pending intents:

- Service
- Activity
- Broadcast Receiver

The onHandleIten can be used to handle the intent

```java
public static final String ACTION_WATER_PLANTS =
                    "com.example.android.mygarden.action.water_plants";

public static void startActionWaterPlants(Context context) {
    Intent intent = new Intent(context, PlantWateringService.class);
    intent.setAction(ACTION_WATER_PLANTS);
    context.startService(intent);
}
``` 

```java
@Override
protected void onHandleIntent(Intent intent) {
    if (intent != null) {
        final String action = intent.getAction();
        if (ACTION_WATER_PLANTS.equals(action)) {
            handleActionWaterPlants();
        }
    }
}
```

##Widget Options##

```java
Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
```

AppWidgetProvider.onappWidgetOptionsChnaged is a place to handle any changes to the options including its size on the desktop. 

##GridViews in Widgets## 

To allow us to use a GridView in a Widget Layout (RemoteViews) we need a RemoteViewsFactory wrapper around the normal adapter. getViewAt allows replaves onBindViewHolder of the adapter. We also need a RemoteViewService which connects RemoteAdapter to request remote views. It will need to be registered in the service node of the manifest. 

##PendingIntentTemplate##

Pending intents are expensive, and if we have one for each element in our grid it would be bad; so this is not allowed. The template is used as a template along with the FillInIntent to allow differences.

In the palce we are creating the RemoteView:

call views.setPendingIntentTemplate(R.id.widget_grid_view, intent);

then in getViewAt add a new intent via views.setOnClickFillInIntent(id, intent)

TODO: Get codeconsolodate example code from last exercise..... 

Now that we’re done, feel free to explore more of the widget capabilities like building animations using ViewFlipper or flipping through photo galleries using Stack Views.

##Other Widget Capabilities##

- Building animations using ViewFlipper
- Flipping through photo galleries using Stack Views