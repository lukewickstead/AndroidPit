# Services #
## Starting Services ##

There are three ways to start a service.

- Start
	- startService(). Allows execute of something when you don't mind when it runs. Also no backwards communication is required.
- Schedule
	- JobSchedule and JobService allow complex conditions for running
- Bind
	- bindService() allows communication to the bound components. A good example is a medai player and its service.


### Using StartService ###

Even though we have inherited IntentService we would still run on the UI / calling thread. Use an AsyncTask to thread the service.

```java
public class StartedService extends IntentService {

    public StartedService() {
        super("StartedService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        XXXHelperAsyncTask.executeTask(this, action);
    }
}
```

This can then be called by this;

```java
Intent intent = new Intent(this, StartedService.class);
intent.setAction(XXXTasks.ACTION_MY_ACTION);
startService(intent);
```