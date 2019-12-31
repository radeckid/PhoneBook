package com.damrad.phonebook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository

    val allContacts: LiveData<List<Contact>>

    init {
        val contactDAO = ContactRoomDatabase.getDatabase(application, viewModelScope).contactDAO()
        repository = ContactRepository(contactDAO)
        allContacts = repository.allContacts
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun selectId(contactID: Int) = viewModelScope.launch {
        repository.selectId(contactID)
    }
}