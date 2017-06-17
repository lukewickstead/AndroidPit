#AsyncTask#

AsyncTask provides an abstraction to creating threads.

##Defining The Task##

The three type parameters are the task parameter type passed into doInBackground,  the type of the progress and the result type of the task which is passed into onPostExecute.

```java
public class XXXTask extends AsyncTask<URL, Void, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Do anything required before the task starts; e.g set the progress bar to visible etc
    }

    @Override
    protected String doInBackground(URL... params) {
    	// Do the work and return the result
    }

    @Override
    protected void onPostExecute(String result) {
    	// Respond to the result
    }
}
```


##Calling The Task##

You can then new up the class and call execute to run it.

```java
new XXXTask().execute(new Url(xxxx));
```