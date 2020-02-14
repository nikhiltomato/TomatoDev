package com.wework.zombiedev.data.db


import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry




import com.wework.zombiedev.data.AppDb

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
abstract class RoomDbAndroidTest {

    protected lateinit var db: AppDb

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().targetContext,AppDb::class.java).build()
    }

    @After
    fun closeDb() {
        db.close()
    }
}