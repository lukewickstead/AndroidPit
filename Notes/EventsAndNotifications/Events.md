#Events#

Hooking up methods to their eventListners can be done in a few ways.

##Implementing OnClickListener##

```java
public class MainActivity extends Activity implements OnClickListener {
    protected void onCreate (...) {
    	button.setOnClickListener(MainActivity.this);
    }
    
    public void onClick(View view) {
    	// Handle click event
    }
}
```

##Anonymous Class##

```java
public class MainActivity extends Activity {
    protected void onCreate (...) {
    	button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                // Handle click event
            }
        });
    }
}
```

##XML Binding##

Or by defining the handler in the XML markup

```xml
 <Button
     android:layout_height="wrap_content"
     android:layout_width="wrap_content"
     android:text="@string/self_destruct"
     android:onClick="onDoSomeEvent" />/>
```

```java
public class MainActivity extends Activity {
    public void onDoSomeEvent(View view) {
    	// Handle click event
    }
}
```