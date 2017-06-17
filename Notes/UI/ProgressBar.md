## Progress Bar ##

The ProgressBar is the spiny progress bar thing on android

```xml
<ProgressBar
     style="@android:style/Widget.ProgressBar.Horizontal"/>
```

Create via the constructor.

```java
private ProgressBar mLoadingIndicator = new ProgressBar();
```

Set visibility with setVisibility.

```java
mLoadingIndicator.setVisibility(View.VISIBLE);
mLoadingIndicator.setVisibility(View.INVISIBLE);
```