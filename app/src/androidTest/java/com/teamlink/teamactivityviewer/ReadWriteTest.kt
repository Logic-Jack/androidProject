package com.teamlink.teamactivityviewer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.teamlink.teamactivityviewer.room.UserDao
import com.teamlink.teamactivityviewer.room.entity.UserEntity
import com.teamlink.teamactivityviewer.services.DataProvider
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReadWriteTest {

        private lateinit var userDao: UserDao
        private lateinit var db: DataProvider

        @Before
        fun createDb() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            db = Room.inMemoryDatabaseBuilder(
                context, DataProvider::class.java).build()
            userDao = db.userDao()
        }

        @After
        @Throws(IOException::class)
        fun closeDb() {
            db.close()
        }

        @Test
        @Throws(Exception::class)
        fun writeUserAndReadInList() {
            val user = UserEntity("4587653", true, "bobby", "102356478", "2022/02/15", "jfhifhffeuhhzeh==")
            userDao.insert(user)
            val userRead = userDao.getUser()
            assertThat(userRead, equalTo(user))
        }
}