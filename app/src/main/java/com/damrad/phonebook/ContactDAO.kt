package com.damrad.phonebook

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDAO {

    @Query("SELECT * from contact_table ORDER BY Surname ASC")
    fun getAlphabetizedContacts(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()

    @Query("DELETE FROM contact_table WHERE Id=:contactId")
    suspend fun deleteContactWithId(contactId: Int)
}
