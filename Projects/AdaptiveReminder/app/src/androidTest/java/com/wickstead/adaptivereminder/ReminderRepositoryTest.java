package com.wickstead.adaptivereminder;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.wickstead.adaptivereminder.data.Reminder;
import com.wickstead.adaptivereminder.data.ReminderMapper;
import com.wickstead.adaptivereminder.data.ReminderRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ReminderRepositoryTest {

    private ReminderRepository mReminderRepository;

    @Before
    public void testSetUp() {
        mReminderRepository = new ReminderRepository(InstrumentationRegistry.getTargetContext());
        mReminderRepository.DeleteAll();

        assertThat(mReminderRepository.GetAll().getCount(), is(0));
    }

    @After
    public void finish() {
        mReminderRepository.DeleteAll();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mReminderRepository);
        assertThat(mReminderRepository.GetAll().getCount(), is(0));
    }

    @Test
    public void canInsertAReminder() {
        // Assign
        String expectedName = "Reminder One";
        int expectedPeriod = 10;

        // Act
        mReminderRepository.Insert(expectedName, expectedPeriod);

        // Assert
        Cursor cursor = mReminderRepository.GetAll();
        ArrayList<Reminder> reminders = ReminderMapper.MapReminders(cursor);
        cursor.close();

        assertThat(reminders.size(), is(1));
        assertThat(reminders.get(0).getId(), greaterThan(0L));
        assertThat(reminders.get(0).getName(), is(expectedName));
        assertThat(reminders.get(0).getEstimatedPeriodMins(), is(expectedPeriod));
    }

    @Test
    public void canUpdateAReminder() {
        // Assign
        String expectedName = "Reminder Two";
        int expectedPeriod = 20;

        long insertedId = mReminderRepository.Insert("Reminder One", 10);

        // Act
        int rowsUpdated = mReminderRepository.Update(insertedId, expectedName, expectedPeriod);

        // Assert
        Cursor cursor = mReminderRepository.GetById(insertedId);
        cursor.moveToFirst();
        Reminder reminder = ReminderMapper.MapReminder(cursor);

        assertThat(rowsUpdated, is(1));
        assertThat(reminder.getName(), is(expectedName));
        assertThat(reminder.getEstimatedPeriodMins(), is(expectedPeriod));
    }

    @Test
    public void canDeleteAReminder() {
        // Assign
        long insertedId = mReminderRepository.Insert("Reminder One", 10);

        // Act
        int rowsDeleted = mReminderRepository.DeleteById(insertedId);

        // Assert
        assertThat(rowsDeleted, is(1));
    }

    @Test
    public void canDeleteAllReminders() {
        // Assign
        mReminderRepository.Insert("Reminder One", 10);
        mReminderRepository.Insert("Reminder Two", 20);

        // Act
        int rowsDeleted = mReminderRepository.DeleteAll();

        // Assert
        assertThat(rowsDeleted, is(2));
    }

}
