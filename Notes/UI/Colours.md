# Colours #

In res/values/colours.xml

```xml
<color name="colorPrimary">#3F51B5</color>
```

ContextCompat.getColor can be used to get a colour from its resource id:

```java
ContextCompat.getColor(context, R.color.aColour)
```