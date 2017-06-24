package com.wickstead.adaptivereminder;

import android.database.Cursor;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.wickstead.adaptivereminder.data.AdaptiveRemindersContract;
import com.wickstead.adaptivereminder.data.ReminderRepository;
import com.wickstead.adaptivereminder.data.RemindersDataContentProvider;
import com.wickstead.adaptivereminder.data.RemindersDbHelper;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RemindersDataContentProviderTests extends ProviderTestCase2<RemindersDataContentProvider> {

    private MockContentResolver mMockResolver;

    public RemindersDataContentProviderTests() {
        super(RemindersDataContentProvider.class, AdaptiveRemindersContract.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mMockResolver = getMockContentResolver();
        RemindersDbHelper mDb = getProvider().getOpenHelperForTest();

        ReminderRepository mReminderRepository = new ReminderRepository(mDb);
        mReminderRepository.Insert("Reminder One", 10);
        mReminderRepository.Insert("Reminder Two", 20);
        mReminderRepository.Insert("Reminder Three", 30);
    }

    public void testCanGetAllReminders() {
        Cursor cursor = mMockResolver.query(
                AdaptiveRemindersContract.REMINDERS_CONTENT_URI,
                null,
                null,
                null,
                AdaptiveRemindersContract.Reminders._ID);

        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(3));
    }
}
