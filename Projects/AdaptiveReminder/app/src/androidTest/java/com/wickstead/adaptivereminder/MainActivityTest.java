package com.wickstead.adaptivereminder;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCanResolveTheCorrectPackageName() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.wickstead.adaptivereminder", appContext.getPackageName());
    }

    @Test
    public void testCanContainListReminders() {
        ViewInteraction listReminders = onView(withId(R.id.list_reminders));
        Assert.assertNotNull(listReminders);
    }

    @Test
    public void testCanStartActivity() {
        assertNotNull(mActivityRule.getActivity());
    }
}
