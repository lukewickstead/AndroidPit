# Espresso #

Espresso is a framework which allows us to test Android applications where the normal testing tools are not enough.
The espresso cheat sheet along with the espresso testing examples are great resources.

- https://developer.android.com/topic/libraries/testing-support-library/index.html#Espresso
- https://github.com/googlesamples/android-testing/tree/master/ui/espresso/IntentsBasicSample
- https://google.github.io/android-testing-support-library/docs/espresso/cheatsheet

## Setting Up ##

Register support annotations and espresso core within the gradle build file.

```gradle
androidTestCompile 'com.android.support:support-annotations:25.1.0'
androidTestCompile 'com.android.support.test.espresso:espresso-core:2.2.2'
```

## AndroidJUnit4 ##

AndroidJUnit4 allows us to control the activation of our Android application along with Espresso components.

```java
@runWidth(AndroidJUnit4.class)
public class MyClass {
}
```

## Testing Views ##

A good example can be found here:

onView and perform can be used to query and act upon our UI views

- https://github.com/googlesamples/android-testing/blob/master/ui/espresso/BasicSample

```java
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivity {

    public static final String STRING_TO_BE_TYPED = "Espresso";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void changeText() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput))
                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.changeTextBt)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));
    }
}
```

## Adapter Views ##

- https://github.com/googlesamples/android-testing/blob/master/ui/espresso/DataAdapterSample

Espresso requires onData() to test ListView and GridView as the item might not be loaded due to their dynamic data loading. onData() will load the item onto the screen before we test using it. 

To help us further specify the item in the AdapterView we’re interested in, we can use a DataOption method such as inAdapterView() or atPosition().

onRow can be used to scroll to a row.

```java
 onRow(TEXT_ITEM_30).onChildView(withId(R.id.rowContentTextView)).perform(click());
```

we can then grab the row for querying.

```java
onData(anything()).inAdapterView(withId(R.id.tea_grid_view)).atPosition(1).perform(click());
``` 


## Testing Intents ##

- Intent Stub
    - Acts as a fake response to an intent
- Intent Response

- https://github.com/googlesamples/android-testing/tree/master/ui/espresso/IntentsBasicSample

@Rule; Instead of the ActivityTestRule that we've seen before, this test uses the IntentsTestRule. This rule is an extension of the ActivityTestRule, which initializes Intents before each Espresso test (@Test) is run and releases the Intent after each test is run. The associated activity is terminated after each test.

initializes Intents before each Espresso test (@Test) is run and releases the Intent after each test is run. The associated activity is terminated after each test.


@Before - stubAllExternalIntents()
As mentioned in the comment in the code snippet below, by default Espresso Intent does not stub any Intents; instead, stubbing must be set up each time a test is run. The method stubAllExternalIntents() makes sure all external Intents are blocked.

It uses the intending() method associated with stubbing and takes not(isInternal()) as its IntentMatcher parameter. isInternal() matches an intent if its package is the same as the target package for the instrumentation test, therefore not(isInternal()) checks that the intent's package does not match the target package for the test. If that's the case respond with:

resultCode - Is the code sent back to the original activity. RESULT_OK indicates the operation was successful.
resultData - Is the data to send back to the original activity. null indicates no data is sent back.

@Before - grantPhonePermission()

Intended for Android M+, ensures permission to use the phone is granted before running the DialerActivityTest.

@Test - pickContactButton_click_SelectsPhoneNumber()

This test mocks a user clicking the "Contact Button" in the DialerActivity, an intent to the ContactsActivity is then stubbed to return a hard-coded VALID_PHONE_NUMBER, and the finally the test checks that the phone number sent back is displayed in the UI.

hasComponent() can match an intent by class name, package name or short class name. Here we match by ShortClassName for the ContactsActivity

```java

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DialerActivityTest {

    private static final String VALID_PHONE_NUMBER = "123-345-6789";

    private static final Uri INTENT_DATA_PHONE_NUMBER = Uri.parse("tel:" + VALID_PHONE_NUMBER);

    private static String PACKAGE_ANDROID_DIALER = "com.android.phone";

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Starting with Android Lollipop the dialer package has changed.
            PACKAGE_ANDROID_DIALER = "com.android.server.telecom";
        }
    }

    /**
     * A JUnit {@link Rule @Rule} to init and release Espresso Intents before and after each
     * test run.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * This rule is based on {@link ActivityTestRule} and will create and launch of the activity
     * for you and also expose the activity under test.
     */
    @Rule
    public IntentsTestRule<DialerActivity> mActivityRule = new IntentsTestRule<>(
            DialerActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));
    }

    @Before
    public void grantPhonePermission() {
        // In M+, trying to call a number will trigger a runtime dialog. Make sure
        // the permission is granted before running this test.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.CALL_PHONE");
        }
    }

    @Test
    public void typeNumber_ValidInput_InitiatesCall() {
        // Types a phone number into the dialer edit text field and presses the call button.
        onView(withId(R.id.edit_text_caller_number))
                .perform(typeText(VALID_PHONE_NUMBER), closeSoftKeyboard());
        onView(withId(R.id.button_call_number)).perform(click());

        // Verify that an intent to the dialer was sent with the correct action, phone
        // number and package. Think of Intents intended API as the equivalent to Mockito's verify.
        intended(allOf(
                hasAction(Intent.ACTION_CALL),
                hasData(INTENT_DATA_PHONE_NUMBER),
                toPackage(PACKAGE_ANDROID_DIALER)));
    }

    @Test
    public void pickContactButton_click_SelectsPhoneNumber() {
        // Stub all Intents to ContactsActivity to return VALID_PHONE_NUMBER. Note that the Activity
        // is never launched and result is stubbed.
        intending(hasComponent(hasShortClassName(".ContactsActivity")))
                .respondWith(new ActivityResult(Activity.RESULT_OK,
                        ContactsActivity.createResultData(VALID_PHONE_NUMBER)));

        // Click the pick contact button.
        onView(withId(R.id.button_pick_contact)).perform(click());

        // Check that the number is displayed in the UI.
        onView(withId(R.id.edit_text_caller_number))
                .check(matches(withText(VALID_PHONE_NUMBER)));
    }
}
```

## Intent Verification Code Example ##

```
@Test
    public void typeNumber_ValidInput_InitiatesCall() {
```

```
androidTestCompile 'com.android.support.test.espresso:espresso-intents:2.2.2'
androidTestCompile 'com.android.support.test:rules:0.5'
androidTestCompile 'com.android.support.test:runner:0.5'
```

## Idling Resource ##

Idling reousces wiat until there are no more messages in the message queue asnd not default tasks in the AsyncTask thread pool. This allows us to pause until in effect are code has finished running before checking the results!!!

## Basic Idling Resource Example App ##

- https://github.com/googlesamples/android-testing/tree/master/ui/espresso/IdlingResourceSample

There are 3 java files - SimpleIdlingResource.java, MainActivity.java, and MessageDelayer.java.

SimpleIdlingResource.java

Implementing the IdlingResource interface is straight forward: it requires completing the 3 required methods. We also created an instance of AtomicBoolean in order to control idleness across multiple threads.

- MainActivity.java

SimpleIdlingResource. Notice that it has an annotation @Nullable which indicates that this variable will be null in production. This is because this setup with IdlingResource is only used for testing, so when the project is run in production, IdlingResource can be null.

MessageDelayer.java

This class is not part of the Android framework. It was created for this project. It takes a String and returns it after a delay via a callback. It executes a long-running operation on a different thread that will cause problems with Espresso if IdlingResource is not implemented and registered.

The processMessage() method takes a String (the one that the user typed into the EditText field), and returns it after the delay time we setup in DELAY_MILLIS. The String is returned via the callback in the onDone method.

processMessage() has 3 parameters - the message, the activity to return back to in the callback, and the IdlingResource.

The if statement checks whether or not IdlingResource is null. If it isn’t we can go ahead and set idle to false. Remember that idle means:

    No UI events in the current message queue
    No more tasks in the default AsyncTask thread pool

So if idle is false there are tasks or events that are happening and any testing should be on halt until these processes finish.

With idle set as false we create a handler and run the method postDelayed().

What exactly is a Handler and what’s the purpose of postDelayed()?

Handler

If we look in the Developer documentation, we can see that there are 2 main uses for a Handler:


- delay execution until later
- enque an action to not be performed until later.


The first action of the Runnable is to check the callback we received in processMessage() (i.e. which activity we should return to after the delay). We return to that activity’s onDone() method and return the message variable.

Then we check that idlingResource is not null. If it's not null we set its state to true. Remember that if the app is idle it means that Espresso gets the green light and continues any action in the test that was queued.

The second parameter, delay time, is set to the constant DELAY_MILLIS in MessageDelayer.java:
Delay time set in MessageDelayer.java

Summary

Let's take a pause and recap what we've uncovered so far.

When the changeTextBt is clicked, onClick() in MainActivity triggers MessageDelayer.processMessage().

processMessage() sets the IdlingResource to false, then creates a Handler which contains a Runnable that will be run after a pre-determined time delay, DELAY_MILLIS. The Runnable that will be executed after the delay:

1) Returns the String entered by the user via a callback to the calling activity (e.g. MainActivity)

2) Sets the IdlingResource to true

Summary

To summarize all the new classes and connections in this specific example:

    Implement the IdlingResource interface (SimpleIdlingResource.java)

    Create a callback interface (MessageDelayer.java) where the actual asynchronous task will occur

    Set the state of IdlingResource to false when the task is running, and then back to true when the task is done

    Have the delayer notify the activity that the process is complete via a callback (MainActivity.onDone)


Dont forget to unregister the idling resource
Register idling resource in gradle dependency

```xml
 compile 'com.android.support.test.espresso:espresso-idling-resource:2.2.2'
 ```
 
 ```java
@After
public void unregisterIdlingResource() {
	if (mIdlingResource != null) {
        	Espresso.unregisterIdlingResources(mIdlingResource);
        }
}
```

## More ##
    
Espresso Web - An API for writing automated tests for apps that contain one or more WebViews. Espresso Web reduce the boilerplate code needed to interact with these WebViews.

- https://google.github.io/android-testing-support-library/docs/espresso/web/
- https://github.com/googlesamples/android-testing/tree/master/ui/espresso/WebBasicSample

Espresso for RecylcerViews - Espresso testing for RecyclerViews works different from testing AdapterViews. It doesn’t use onData; instead, has actions that scroll to positions or perform actions on items.

- https://google.github.io/android-testing-support-library/docs/espresso/lists/#recyclerviews
- https://developer.android.com/studio/test/espresso-test-recorder.html

## Espresso Test Recorder ##

Android Studio also has a an Espresso Test Recorder which allows you to create UI tests by simply recording your interactions on a device and the Test Recorder will autogenerate the test code for you!

The tests are written using the same Espresso Testing framework that we just covered. At the time of creating this content there are certain limitations to the Test Recorder (e.g. it can't yet handle IdlingResources, it has limited number of assertions available). However, armed with the knowledge of how to write your own Espresso tests from scratch, you’ll be better equipped to understand, modify, and update any auto-generated tests if you do decide to use the Test Recorder.

https://developer.android.com/studio/test/espresso-test-recorder.html

RecyckerView

https://google.github.io/android-testing-support-library/docs/espresso/lists/#recyclerviews

## UI Automator ##

UI Automator - Framework for cross-app functional UI testing between the system and installed apps
https://developer.android.com/topic/libraries/testing-support-library/index.html#UIAutomator