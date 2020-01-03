package com.damrad.phonebook

import androidx.lifecycle.LiveData

class ContactRepository(private val contactDAO: ContactDAO) {

    val allContacts: LiveData<List<Contact>> = contactDAO.getAlphabetizedContacts()

    suspend fun insert(contact: Contact) {
        contactDAO.insert(contact)
    }

    suspend fun deleteContactWithId(id: Int) {
        contactDAO.deleteContactWithId(id)
    }

}