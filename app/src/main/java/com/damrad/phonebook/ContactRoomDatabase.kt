package com.damrad.phonebook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
public abstract class ContactRoomDatabase : RoomDatabase() {

    abstract fun contactDAO(): ContactDAO

    private class ContactDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.contactDAO())
                }
            }
        }

        suspend fun populateDatabase(contactDao: ContactDAO) {
            // Delete all content here.
            contactDao.deleteAll()

        }
    }

    companion object {

        @Volatile
        private var INSTANCE: ContactRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ContactRoomDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, ContactRoomDatabase::class.java, "contact_table")
                    .addCallback(ContactDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}