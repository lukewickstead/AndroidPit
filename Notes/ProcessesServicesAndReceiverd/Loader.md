# Loader #

Loaders are a light weight way of freeing up the UI thread.

Use the LoaderManager to register that you activity needs a loader. Then override onCreateLoader to create the loader and onLoadFinished to receive the results.

```java
public void DoSomething() {
    Bundle bundle = new Bundle();
    bundle.putString("BundleKey", "value");

    LoaderManager loaderManager = getSupportLoaderManager();
    Loader<String> loader = loaderManager.getLoader(UNIQUE_LOADER_ID);
    if (loader == null) {
        loaderManager.initLoader(UNIQUE_LOADER_ID, bundle, this);
    } else {
        loaderManager.restartLoader(UNIQUE_LOADER_ID, bundle, this);
    }
}

@Override
public Loader<String> onCreateLoader(int id, final Bundle args) {
    return new AsyncTaskLoader<String>(this) {
        @Override
        protected void onStartLoading() {
        	// Do anything like setting progress bar visibility
            if (mCachedResult != null) {
            	deliverResult(mCachedResult);
            } else {
            	forceLoad(); // ??? seems we sometimes need to force reloading
            }
        }

        @Override
        public String loadInBackground() {
			// Do something
        }

        @Override
        public void deliverResult(String result) {
			// Allows forcing data to listeners of the result
			mCachedResult = result;
            super.deliverResult(result);
        }
    };
}

@Override
public void onLoadFinished(Loader<String> loader, String data) {
	// handle the results
}
```