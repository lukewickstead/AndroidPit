# Calling Web Services #
## Third Party Frameworks ##

Consider Retrofit or Spring when calling web services.

## Uri Builder ##

URLs can be built from Uri.

```java
public static URL buildUrl(String githubSearchQuery) {
    Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
            .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
            .appendQueryParameter(PARAM_SORT, sortBy)
            .build();

    URL url = null;
    try {
        url = new URL(builtUri.toString());
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }

    return url;
}
```

## Scanner ##

Scanner can be used to stream the response from a http request. The delimiter of \A is used to read all of the stream in one go. 

```java
public static String getResponseFromHttpUrl(URL url) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
        InputStream in = urlConnection.getInputStream();

        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
            return scanner.next();
        } else {
            return null;
        }
    } finally {
        urlConnection.disconnect();
    }
}
```
