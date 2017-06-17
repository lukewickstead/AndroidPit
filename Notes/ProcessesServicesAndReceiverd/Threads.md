# Android Processes and Threads #
## Application Process Model ##

Applications in Android run in a sandbox; unique identity, process and file system.

- Process. Each application is run in a unique process powered by a Linux Kernel process running the Dalvik. Dalvik is a JVM for Android. Usually run in its own process

- User identity; each application is given it's unique user id.

- File System; each app and resources are stored in an area which only the applications user id can run from.

Inter-Process Interactions can be performed:

- Intent
- Messenger
- ContentResolver
- IBinder


## Sharing User IDs ##

In the manifest we can request application to share the same user id. A unique id is only generated upon first installation. Additional installations will then use this assign id

```xml 
<manifest
    android:sharedUserId="xxx.xxx.xxx">
</manifest>
```
 
Apps doing this must be signed by the same key; prevents people trying to run in your user id

You will need to uninstall the application when giving it or changing the sharedUserId.
 
They will still run in different process id. 

## Sharing Processes ## 

Define a process name in <application android:process="xxx.xxx.xxx"> tag of the manifest. 

Uses as reference only; the the actual process used. 

Start with : to be private to your application and cannot be shared

Start with lower case character for a global sharable process.

They must share the same userid and therefore signed with the same certificate

Ding this can save ram and memory input and can reduce IPC for custom components

- IBinder
    - Memmory still needs to be marshaled and process context switched so even though IBinder is IPC efficient gains can still be made
- ContentProvider
    - They are process. Memmory still needs to be marshaled and process context switched. Gains can still be made.

This can be verified buy checking the passed in IBdinder into the application

## Threads and Thread Options ##

Each application uses:

- Main UI thread
- Thread pool used by framework

### UI Thread ###

Rendering widgets, executes Activity. By default many things will run on the main thread including Service / BoradcastReceiver. Best to spawn threads!!! Use worker threads!

ANR: Application Not Responding.

- Fail to respond to user input after timeout
- BroadcastReceiver fails to complete after timeout
- Critical error
 
AND is "Do you want to close the application"

We have a few options:

- Thread/Looper/Handler
- HandlerThread
- AsyncTask
- ThreadPools
- Native thread

## Thread class, defined by Java ##

- Executead a specific Runnable.
- Base class for other threading options
    - Primitive and not hooked into framework
- Single invocation

## Thread Handler ##

- Looper bound to a specific thread instance
- Looper waits for message
- Looper hands message objects to handler
- Extending Handler or provide a Handler.Callback
    - Message contents
    - Can execute runnables in handler
- Not tied to framework states


TODO: Get Example. Quite overly complex for acheiving little

## HandlerThread ##

This is basically an abstraction around Thread/Looper/Handler construct.

Automatically creates Thread and Looper.

```java
private class DataGenThread extends Thread implements Handler.Callback {
    private Looper mWorkerLooper;
    protected Handler mWorkerHandler;

    @Override
    public void run() {
        Looper.prepare();
        mWorkerLooper = Looper.myLooper();
        mWorkerHandler = new Handler(mWorkerLooper, this);
        Looper.loop();
    }

    @Override
    public boolean handleMessage(Message msg) {
        // ...
        return true;
    }
}

``` 

Extend class HandlerThread and provide onLooperPrepared.

Single invocation and can be long running.

Up to developer to quite and clear up any resources. It is not life cycle aware!! 

```java
private class DataGenThread extends HandlerThread implements Handler.Callback {
    protected Handler           mWorkerHandler;

    public DataGenThread(String name) {
                                super(name);
                }

    public DataGenThread(String name, int priority) {
                                super(name);
                }

    @Override
    protected void onLooperPrepared() {
                mWorkerHandler = new Handler(getLooper(), this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        // ...       
        return true;
    }
}
```   

## AsyncTask ##

https://developer.android.com/reference/android/os/AsyncTask.html

Android provides AsyncTask. Automatically handles coordination with the UI thread.

Should be used for short lives operations only even though they use another thread.

They have some restrictions on their creation and execute. Can only be created on the UI thread

Originally they were serially executed on the background thread. Since 4 (Doughnut ) used parallel execution. It now uses a pool of threads. Honeycomb reverted to serial due to peoples code breaking. Can be overridden to allow parallel.

Single invocation only; they are one shot only.

```java
private class GenAsyncTask extends AsyncTask<byte[], Integer, Long> {
	@Override
        protected Long doInBackground(byte[]... arg0) { 
		for (int i = 0; i < 100; i++) {
			if (isCancelled()) {
		        	//  We have been cancelled, exit.
		        	break;
			}	

			publishProgress(i);
		}
		return 100;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
                mProgress.setProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(Long result) {
		mProgressTxt.setText("Done!");
		mGenAT = null;
	}
}
```

- isCancelled can be used to see if we have been canceled.
- onProgressUpdate is called when we publish the progress.
- onPostExecute is done when we end being passed in the result

// TODO: Get all versions examples.
 
## Thread Pools ##

Thread Pool is managed by the executor service.

Flexible and powerful and not run by the UI thread so we can perform longer running tasks from them.

Allows parallel operations.

We can coordinate with the UI to update it.

Efficient thread usage via re-use.

Thread pool, can reject work if resources are under pressure. Can even cancel threads

Can set pool size.

Implement Runnable.

```java
private class MyRunnable implements Runnable {
        private int    mKbStart;
	public MyRunnable(int kbStart) {
                mKbStart = kbStart;
	}

         @Override
        public void run() {
		doGenerate(mKbStart);
                mHandler.obtainMessage(MSG_PROG_UPDATE).sendToTarget();
        }
}
```

 We run through the Executive 

```java
mExec = Executors.newFixedThreadPool(numProcessors);
MyRunnable myRunnable = new MyRunnable(i);
if (mExec.submit(myRunnable) == null)
```

Coordination between thread and UI is done by the handler still

```java
mHandler = new Handler(this);

// into thread
mHandler.obtainMessage(MSG_GEN_STARTING).sendToTarget();

//  inside thread
mHandler.obtainMessage(MSG_PROG_UPDATE).sendToTarget();

``` 

They still need to be destroyed as they are not life cycle aware