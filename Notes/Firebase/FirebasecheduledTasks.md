# Firebase Scheduled Tasks #

Firebase can be used to schedule tasks. The following steps are required:


- Register Firebase And Google Play Services
- Creating A Firebase Job Service
- Register Firebase Job Service In Te Manifest Declaration
- Register & Configure Firebase Job Service With The Dispatcher##

## Register Firebase And Google Play Services ##

Information can be found here:

- https://firebase.google.com/docs/android/setup

You will also need to create a Google Play Services account. 

## Creating A Firebase Job Service ##

```java
public class XXXJobService extends JobService {
    private AsyncTask mBackgroundTask;
    
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
		// Do something
		return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
```

## Register Firebase Job Service In The Manifest Declaration ##

```xml
<service
	android:name=".XXXJobService"
	android:exported="false">
    <intent-filter>
        <action android:name="com.firebase.jobdispatcher.ACTION_EXECUTE"/>
    </intent-filter>
</service>
```

## Register & Configure Firebase Job Service With The Dispatcher ##

```java
private static final int REMINDER_INTERVAL_MINUTES = 15;
private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

private static final String REMINDER_JOB_TAG = "xxx_reminder_tag";

Driver driver = new GooglePlayDriver(context);
FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

Job constraintReminderJob = dispatcher.newJobBuilder()
        .setService(XXXJobService.class)
        .setTag(REMINDER_JOB_TAG)
        .setConstraints(Constraint.DEVICE_CHARGING)
        .setLifetime(Lifetime.FOREVER)
        .setRecurring(true)
        .setTrigger(Trigger.executionWindow(
                REMINDER_INTERVAL_SECONDS,
                REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
        .setReplaceCurrent(true)
        .build();

dispatcher.schedule(constraintReminderJob);
```