#Pending Intents#

A pending intent is an intent which will you can be defined to be performed later by another activity on your behalf and using your activity's permissions and identity.

##Creating Pending Intents##

Below we are asking the manager to perform an intent in a predefined amount of time.

```java
Uri webpage = Uri.parse("http://bbb.co.uk);
Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

long alarmTime = SystemClock.elapsedRealtime() + 5000;
alarmManager.set(AlarmManager.ELAPSED_REALTIME, alarmTime, pi);
```

##Pending Intent Flags##

The last parameter to PendingIntent.getActivity defines what to do if multiple intents are being raised:

- FLAG_UPDATE_CURRENT
	- Update any existing intent with the extras for this new intent
- FLAG_NO_CREATE
	- Do not create one but return a reference to the existing intent
- FLAG_CANCEL_CURRENT
	- Create a new intent and cancel any previously existing intent
- FLAG_ONE_SHOT
	- The intent can only be used once. If a matching pending intent exists that does not include this flag then a new one is created.

Intents are considered equal based upon the resolution of their intent filters.

##Pending Intents & Notifications##

Pending intents can be sent to the notification manager.

Up to three actions can be defined along with the notification; an action wraps an intent

```java
public class NotificationUtils {

    private static final int REMINDER_NOTIFICATION_ID = 1138;
    /**
     * This pending intent id is used to uniquely reference the pending intent
     */
    private static final int REMINDER_PENDING_INTENT_ID = 3417;
    private static final int ACTION_DO_PENDING_INTENT_ID = 1;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void notifyXXX(Context context) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.reminder_notification_title))
                .setContentText(context.getString(R.string.reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(doAction(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }


        NotificationManager notificationManager =
        	(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }
    
    private static Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context, XXXIntentService.class);
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Action ignoreReminderAction = new Action(R.drawable.ic_cancel_black_24px,
                "No, thanks.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    private static Action doAction(Context context) {
        Intent doIntent = new Intent(context, XXXIntentService.class);
        doIntent.setAction(ReminderTasks.ACTION_DO);
        PendingIntent doActionPendingIntent = PendingIntent.getService(
                context,
                ACTION_DO_PENDING_INTENT_ID,
                doIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Action doAction = new Action(R.drawable.ic_local_drink_black_24px,
                "I did it!",
                doActionPendingIntent);
        return doAction;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_icon_24px);
        return largeIcon;
    }
}
```